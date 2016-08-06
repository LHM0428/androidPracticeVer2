package com.practice.lee.gameexample2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Lee on 2016-08-06.
 */
public class Ball implements DrawableItem {
    private float mX;
    private float mY;
    private float mSpeedX;
    private float mSpeedY;
    private float mRadius;

    public Ball(float mRadius, float initialX, float initialY) {
        this.mRadius = mRadius;
        mSpeedX = mRadius/5;
        mSpeedY = -mRadius/5;
        mX = initialX;
        mY = initialY;
    }

    public void move(){
        mX += mSpeedX;
        mY += mSpeedY;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mX,mY,mRadius,paint);
    }

    public void setmX(float mX) {
        this.mX = mX;
    }

    public void setmY(float mY) {
        this.mY = mY;
    }

    public void setmSpeedX(float mSpeedX) {
        this.mSpeedX = mSpeedX;
    }

    public void setmSpeedY(float mSpeedY) {
        this.mSpeedY = mSpeedY;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public float getmX() {
        return mX;
    }

    public float getmY() {
        return mY;
    }

    public float getmSpeedX() {
        return mSpeedX;
    }

    public float getmSpeedY() {
        return mSpeedY;
    }

    public float getmRadius() {
        return mRadius;
    }
}
