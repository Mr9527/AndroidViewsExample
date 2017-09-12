package com.qhfax.androidviewsexample.canvas.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author chenzhaojun
 * @date 2017/9/12
 * @description
 */

public class BezierView extends View {

    public static final int SECOND_ORDER_BEZIER = 1;
    public static final int THIRD_ORDER_BEZIER = 2;
    private boolean isFirstContrl;

    @IntDef({SECOND_ORDER_BEZIER, THIRD_ORDER_BEZIER})
    @Retention(RetentionPolicy.SOURCE)
    private @interface BezierType {
    }


    private Paint mPaint;
    private Path mBezierPath;
    private PointF start, end, control, control2;
    private int type = SECOND_ORDER_BEZIER;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);

        mBezierPath = new Path();
        start = new PointF();
        end = new PointF();
        control = new PointF();
        control2 = new PointF();
    }

    public void setType(@BezierType int type) {
        this.type = type;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int centerX = w / 2;
        int centerY = h / 2;

        start.x = centerX - 200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;
        control.x = centerX - 200;
        control.y = centerY - 200;
        control2.x = centerX + 200;
        control2.y = centerY + 200;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isFirstContrl = Math.abs(x - control.x) <= 50 && Math.abs(y - control.y) <= 50;
                break;
            case MotionEvent.ACTION_MOVE:
                if (type == SECOND_ORDER_BEZIER || isFirstContrl) {
                    control.x = x;
                    control.y = y;
                } else {
                    control2.x = x;
                    control2.y = y;
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (type) {
            case SECOND_ORDER_BEZIER:
                secondOrderBezierView(canvas);
                break;
            case THIRD_ORDER_BEZIER:
                thirdOrderBezier(canvas);
                break;
        }
    }

    private void thirdOrderBezier(Canvas canvas) {
        mBezierPath.reset();
        mPaint.setStrokeWidth(16);
        mPaint.setColor(Color.GRAY);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(control.x, control.y, control2.x, control2.y, mPaint);
        canvas.drawLine(end.x, end.y, control2.x, control2.y, mPaint);

        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
        mBezierPath.moveTo(start.x, start.y);
        mBezierPath.cubicTo(control.x, control.y, control2.x, control2.y, end.x, end.y);
        canvas.drawPath(mBezierPath, mPaint);
    }

    private void secondOrderBezierView(Canvas canvas) {
        mBezierPath.reset();
        mPaint.setStrokeWidth(16);
        mPaint.setColor(Color.GRAY);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);
        mBezierPath.moveTo(start.x, start.y);
        mBezierPath.quadTo(control.x, control.y, end.x, end.y);

        canvas.drawPath(mBezierPath, mPaint);
    }
}
