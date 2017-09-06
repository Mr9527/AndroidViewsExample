package com.qhfax.androidviewsexample.canvas.path;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qhfax.androidviewsexample.R;

/**
 * @author chenzhaojun
 * @date 2017/9/5
 * @description
 */

public class PathView extends View {

    public static final int HEART = 590;

    private Path path;
    private Paint paint;
    private int loadType;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        path = new Path();
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PathView, defStyle, 0);
        loadType = typedArray.getInt(R.styleable.PathView_type, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (loadType) {
            case HEART:
                drawHeart(canvas);
                break;
            default:
                drawHeart(canvas);
                break;
        }
    }


    private void drawHeart(Canvas canvas) {
        paint.setColor(Color.RED);
        RectF rect = new RectF(100, 100, 200, 200);
        RectF toRect = new RectF(200, 200, 400, 400);
        path.addArc(rect, -220, 220);
        path.addArc(toRect, -225, 220);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(toRect, paint);
        canvas.drawRect(rect, paint);
    }
}
