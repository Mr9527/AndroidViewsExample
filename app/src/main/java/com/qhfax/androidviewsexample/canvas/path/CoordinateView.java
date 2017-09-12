package com.qhfax.androidviewsexample.canvas.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qhfax.androidviewsexample.SizeUtils;

/**
 * @author chenzhaojun
 * @date 2017/9/12
 * @description
 */

public class CoordinateView extends View {
    private final static String PROMPT = "100";

    private int width;
    private int height;
    private Paint mPaint;
    private Paint mEffectPaint;
    private Path mTrianglePath;
    private RectF mCanvasRect;
    private Rect mTextRect;

    public CoordinateView(Context context) {
        this(context, null);
    }

    public CoordinateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        mTrianglePath = new Path();
        mEffectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEffectPaint.setStrokeWidth(2);
        mEffectPaint.setColor(Color.WHITE);
        PathEffect pathEffect = new DashPathEffect(new float[]{8, 8}, 1);
        mEffectPaint.setPathEffect(pathEffect);
        mCanvasRect = new RectF(3, 3, 300, 300);
        mTextRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(format(150), format(150));
        //绘制色块
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(mCanvasRect, mPaint);

        //绘制坐标轴
        mPaint.setColor(Color.RED);
        canvas.drawLine(-100, 0, 400, 0, mPaint);
        canvas.drawLine(0, -100, 0, 400, mPaint);

        //绘制箭头
        mTrianglePath.moveTo(400, -10);
        mTrianglePath.lineTo(400, 10);
        mTrianglePath.lineTo(410, 0);
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaint);
        mTrianglePath.moveTo(-10, 400);
        mTrianglePath.lineTo(10, 400);
        mTrianglePath.lineTo(0, 410);
        mTrianglePath.close();
        canvas.drawPath(mTrianglePath, mPaint);

        //绘制圆和线
        mPaint.setColor(Color.DKGRAY);
        canvas.drawCircle(100, 100, 80, mPaint);
        canvas.drawLine(3, 100, 100, 100, mEffectPaint);
        canvas.drawLine(100, 3, 100, 100, mEffectPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(2);
        canvas.drawLine(100, 100, 180, 100, mPaint);

        //绘制文本
        mPaint.setColor(Color.DKGRAY);
        mPaint.getTextBounds(PROMPT, 0, PROMPT.length(), mTextRect);
        canvas.drawText(PROMPT, 100 - mTextRect.right / 2, -10, mPaint);
        canvas.drawText(PROMPT, -mTextRect.right - 10, 100 + mTextRect.bottom, mPaint);
    }

    int format(float size) {
        return SizeUtils.dp2px(getContext(), size);
    }
}
