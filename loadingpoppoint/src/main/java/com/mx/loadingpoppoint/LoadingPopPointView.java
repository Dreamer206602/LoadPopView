package com.mx.loadingpoppoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boobooL on 2016/5/16 0016
 * Created 邮箱 ：boobooMX@163.com
 */
public class LoadingPopPointView extends View {

    public static final float MAX = 30;
    public static final int STEP = 1;

    public static final int RADIUS = 80;
    public static final float MOVING_RADIUS = 10f;
    public static final float INCREASE = MOVING_RADIUS * 0.4f;

    public double x;
    public double y;

    private float radius;
    private static final int DEFAULT_NUMBER = 3;
    private int pointNumber = DEFAULT_NUMBER;

    //default colors
    private int paintColor[] = {
            Color.parseColor("#3B8EFF"),
            Color.parseColor("#FFD81D"),
            Color.parseColor("#FF4A4B")};
    private float progress[] = {0, 10, 20};
    private List<Paint> mPaints;//画笔的集合

    public LoadingPopPointView(Context context) {
        super(context);
        init();
    }

    public LoadingPopPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPopPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //init
    private void init() {
        //init Paint and put the paint into  ArrayList meanwhile Paint.setColor
        mPaints = new ArrayList<>();
        for (int i = 0; i < pointNumber; i++) {
            Paint paint = new Paint();
            paint.setColor(paintColor[i]);
            mPaints.add(paint);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < pointNumber; i++) {
            progress[i] = progress[i] + STEP;
            radius = getRadiusByProgress(progress[i]);
            canvas.drawCircle((float) (getWidth() / 2 + x), getHeight() / 2,
                    radius, mPaints.get(i));
        }
        // redraw the view per 40 milliseconds,and show the animation
        postInvalidateDelayed(40);
    }

    /**
     * @param progress
     * @return
     */
    private float getRadiusByProgress(float progress) {
        // this x and y is global
        x = RADIUS * Math.cos((Math.toRadians(progress % MAX / MAX * 360)));
        y = RADIUS * Math.sin((Math.toRadians(progress % MAX / MAX * 360)));

        if (y < 0) {
            return (float) (MOVING_RADIUS + (1 - Math.abs(x) / RADIUS) * INCREASE);
        } else {
            return (float) (MOVING_RADIUS - (1 - Math.abs(x) / RADIUS) * INCREASE);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(
               measure(widthMeasureSpec,true),
               measure(heightMeasureSpec,false));
    }

    private int measure(int measureSpec,boolean isWidth){
        int result;
        int mode=MeasureSpec.getMode(measureSpec);
        int size=MeasureSpec.getSize(measureSpec);
        int padding=isWidth?getPaddingLeft()+getPaddingRight():
                getPaddingTop()+getPaddingBottom();
        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            result=isWidth?getSuggestedMinimumWidth():
                    getSuggestedMinimumHeight();
            result+=padding;
            if(mode==MeasureSpec.AT_MOST){
                if(isWidth){
                    result=Math.max(result,size);
                }else{
                    result=Math.min(result,size);
                }
            }
        }
        return result;
    }
}
