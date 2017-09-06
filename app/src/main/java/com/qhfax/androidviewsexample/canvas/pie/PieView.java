package com.qhfax.androidviewsexample.canvas.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzhaojun
 * @date 2017/7/10
 * @description
 */

public class PieView extends View {

    private List<PieData> mData;

    private float mStartAngel = -225;

    private Paint mPaint;
    private Paint mLinePaint;

    private int mHeight;

    private int mWidth;

    private RectF mRectF;

    private float radius;

    private PieChartAnimation mAnimation;
    private boolean isDrawLine = true;

    /**
     * 动画时间
     */
    private static final long ANIMATION_DURATION = 1000;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setColor(Color.WHITE);
        mAnimation = new PieChartAnimation();
        mAnimation.setDuration(ANIMATION_DURATION);
        testInitData();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        radius = Math.min(mWidth, mHeight) / 2 - 10;
        mRectF.left = -radius;
        mRectF.top = -radius;
        mRectF.right = radius;
        mRectF.bottom = radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null) {
            return;
        }
        float currentStartAngle = mStartAngel;
        canvas.translate(mWidth / 2, mHeight / 2);
        for (int i = 0; i < mData.size(); i++) {
            PieData data = mData.get(i);
            mPaint.setColor(data.getColor());
//            calcArcEndPointXY(0, 0, radius / 40, -180);
//            mRectF.left = posX - radius;
//            mRectF.top = posY - radius;
//            mRectF.right = posX + radius;
//            mRectF.bottom = posY + radius;
            canvas.drawArc(mRectF, currentStartAngle, data.getAngle(), true, mPaint);
            if (isDrawLine && data.isDrawLine()) {
                canvas.drawArc(mRectF, currentStartAngle , data.getAngle() , true, mLinePaint);
            }
            currentStartAngle += data.getAngle();

        }
    }

    public void setData(List<PieData> mData) {
        this.mData = mData;
        initData(mData);
        startAnimation(mAnimation);
        invalidate();
    }

    private double sumValue = 0;

    private void initData(List<PieData> data) {
        if (mData == null || mData.size() == 0) {
            return;
        }
        sumValue = 0;
        int drawLine = 0;
        float sumAngle = 0;
        for (int i = 0; i < data.size(); i++) {
            sumValue += data.get(i).getValue();
        }
        for (int i = 0; i < data.size(); i++) {
            PieData pieData = data.get(i);
            float percentage = (float) (pieData.getValue() / sumValue);
            float angle = percentage * 360;
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
            //角度太小时不画白色分割线
            if (angle > 5) {
                pieData.setDrawLine(true);
                drawLine++;
            }
            sumAngle += angle;
            if (pieData.getColor() == 0) {
                pieData.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
        }
        if (drawLine <= 1) {
            isDrawLine = false;
        }
    }


    /**
     * 自定义动画
     */
    public class PieChartAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                for (int i = 0; i < mData.size(); i++) {
                    PieData data = mData.get(i);
                    //通过总和来计算百分比
                    float percentage = (float) (data.getValue() / sumValue);
                    //通过百分比来计算对应的角度
                    float angle = percentage * 360;
                    //根据插入时间来计算角度
                    angle = angle * interpolatedTime;
                    data.setAngle(angle);
                }
            } else {//默认显示效果
                for (int i = 0; i < mData.size(); i++) {
                    //通过总和来计算百分比
                    PieData data = mData.get(i);
                    float percentage = (float) (data.getValue() / sumValue);
                    //通过百分比来计算对应的角度
                    float angle = percentage * 360;
                    data.setPercentage(percentage);
                    data.setAngle(angle);
                }
            }
            invalidate();
        }
    }

    private void testInitData() {
        List<PieData> list = new ArrayList<>();
        list.add(new PieData("java", 30832));
        list.add(new PieData("kotlin", 2782));
        list.add(new PieData("C/C++", 25803));
        list.add(new PieData("JavaScript", 28803));
        list.add(new PieData("Python", 20000));
        setData(list);
    }

}
