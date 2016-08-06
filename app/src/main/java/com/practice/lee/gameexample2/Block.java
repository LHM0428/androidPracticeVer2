package com.practice.lee.gameexample2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Lee on 2016-08-05.
 * Block Class
 */
public class Block implements DrawableItem {
    private final float mTop;
    private final float mLeft;
    private final float mBottom;
    private final float mRight;
    private int mHard;


    public Block(float mTop, float mLeft, float mBottom, float mRight) {
        this.mTop = mTop;
        this.mLeft = mLeft;
        this.mBottom = mBottom;
        this.mRight = mRight;
        this.mHard = 1;
    }

    public void draw(Canvas canvas, Paint paint){
        if(mHard>0){
            //내구성이 0 이상인 경우만 그린다.
            canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);
            //테두리 선을 그린다.
            paint.setColor(Color.rgb(255, 204, 0));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5f);
            canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);
        }
    }
}
