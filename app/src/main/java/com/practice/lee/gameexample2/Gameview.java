package com.practice.lee.gameexample2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Lee on 2016-08-05.
 * Class for View
 */
public class GameView extends TextureView implements TextureView.SurfaceTextureListener, View.OnTouchListener{

    private Thread mThread;
    volatile  private boolean mIsRunnable;
    volatile private float mTouchedX;
    volatile private float mTouchedY;
    private ArrayList<DrawableItem> mItemList;
    private Pad mPad;
    private float mPadHalfWidth;
    private Ball mBall;
    private float mBallRadius;

    public GameView(Context context) {
        super(context);
        setSurfaceTextureListener(this);
        setOnTouchListener(this);
    }

    public void start(){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long startTime = System.currentTimeMillis(); // 최적화를 위해 시작 시간을 잰다.
                    synchronized (GameView.this) {
                        if(!mIsRunnable){
                            break;// mIsRunnable==false일 때 종료한다.
                        }
                        //앱 실행 중에 반복해서 호출된다.
                        Canvas canvas = lockCanvas();
                        Paint paint = new Paint();
                        if (canvas == null) {
                            continue;// 캔버스를 가져올 수 없을 때 루프를 다시 시작
                        }
                        canvas.drawColor(Color.BLACK);

                        //패드를 그린다.
                        float padLeft = mTouchedX - mPadHalfWidth;
                        float padRight = mTouchedX + mPadHalfWidth;
                        mPad.setLeftRight(padLeft, padRight);

                        mBall.move();
                        float ballTop = mBall.getmY() - mBallRadius;
                        float ballBottom = mBall.getmY() + mBallRadius;
                        float ballLeft = mBall.getmX() - mBallRadius;
                        float ballRight = mBall.getmY() + mBallRadius;
                        if(ballLeft < 0 && mBall.getmSpeedX()<0 || ballRight >= getWidth() && mBall.getmSpeedX() >0){
                            mBall.setmSpeedX(-mBall.getmSpeedX()); // 가로 방향 벽에 부딪혔으므로 가로 속도를 반전
                        }
                        if(ballTop < 0 || ballBottom > getHeight()){
                            mBall.setmSpeedY(-mBall.getmSpeedY()); // 세로 방향 벽에 부딪혔으므로 세로 속도 반전
                        }

                        int i = 200;
                        for (DrawableItem item : mItemList) { //mItemList의 내용이 하나씩 block에 전달된다
                            paint.setStyle(Paint.Style.FILL);
                            paint.setColor(Color.rgb(255, i--, 153));
                            item.draw(canvas, paint);
                        }
                        unlockCanvasAndPost(canvas);

                        long sleepTime = 16 - (System.currentTimeMillis() - startTime); // while 끝나는 시간을 잰다
                        if(sleepTime > 0){ // 만약 16밀리초(1/60초) 보다 빠르게 끝났을 경우 그 만큼의 시간을 대기시킨다.
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }//synchronized
                }//while
            }//run
        });//thread
        mIsRunnable = true;
        mThread.start();
    }

    public void stop(){
        mIsRunnable = false;
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        readyObjects(width,height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        readyObjects(width,height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (this) {
            return true;
        }
    }
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mTouchedX = event.getX();
        mTouchedY = event.getY();
        return true;
    }

    public void readyObjects(int width, int height){
        float blockWidth = width/10;
        float blockHeight = height/20;
        mItemList = new ArrayList<DrawableItem>();
        for(int i=0; i<100; i++){
            float blockTop = i/10*blockHeight;
            float blockLeft = i%10*blockWidth;
            float blockBottom = blockTop + blockHeight;
            float blockRight = blockLeft + blockWidth;
            mItemList.add(new Block(blockTop,blockLeft,blockBottom,blockRight));
        }
        mPad = new Pad(height*0.8f, height*0.85f);
        mPadHalfWidth = width / 10;
        mItemList.add(mPad);

        mBallRadius = width < height ? width/40 : height/40;
        mBall = new Ball(mBallRadius, width/2, height/2);
    }
}
