package com.qhfax.androidviewsexample.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * @author chenzhaojun
 * @date 2017/7/11
 * @description
 */

public class StatusView extends android.support.v7.widget.AppCompatTextView {
    private Paint mTextPaint;
    private Paint mPaint;
    private Rect mRect;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTextPaint = new Paint();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#FF5C1A"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getTextColors().getDefaultColor());
        mTextPaint.getTextBounds(getText().toString(), 0, getText().length(), mRect);
        Path path = new Path();
        int x = mRect.right / 10;
        path.moveTo(x, mRect.bottom);
        path.lineTo(mRect.right+x, mRect.bottom);
        path.lineTo(mRect.right-x, -mRect.top * 2);
        path.lineTo(0, -mRect.top * 2);
        path.close();
        canvas.drawPath(path, mPaint);
        super.onDraw(canvas);
    }
}
