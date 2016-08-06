package com.practice.lee.gameexample2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Lee on 2016-08-06.
 * Pad Class
 */
public class Pad implements DrawableItem {
    private final float mTop;
    private float mLeft;
    private final float mBottom;
    private float mRight;

    public Pad(float mTop, float mBottom) {
        this.mTop = mTop;
        this.mBottom = mBottom;
    }

    public void setLeftRight(float left, float right){
        mLeft = left;
        mRight = right;
    }

    public void draw(Canvas canvas, Paint paint){
        //색칠하는 부분 그리기
        paint.setColor(Color.rgb(179, 255, 102));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
    }


}
