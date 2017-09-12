package com.qhfax.androidviewsexample.progress;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.qhfax.androidviewsexample.R;

/**
 * @author chenzhaojun
 * @date 2017/9/11
 * @description
 */

public class VIPLevelProgressView extends ProgressBar {

    private static final int IMAGE[] = {R.drawable.vip_regular_members, R.drawable.vip_silver_member, R.drawable.vip_gold_membership, R.drawable.vip_diamond_membership};
    private Paint mPaint;
    private Bitmap[] bitmaps;
    private int current;
    private Rect mSrcRect;
    private RectF mDestRect;
    private ValueAnimator ScaleAnim;


    public VIPLevelProgressView(Context context) {
        this(context, null);
    }

    public VIPLevelProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VIPLevelProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            return;
        }
        bitmaps = new Bitmap[IMAGE.length];
        for (int i = 0; i < IMAGE.length; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), IMAGE[i]);
        }
        mPaint = new Paint();
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mSrcRect = new Rect();
        mDestRect = new RectF();
        ScaleAnim = ValueAnimator.ofFloat();
        ScaleAnim.setFloatValues();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getPaddingLeft() + 5, 0);
        if (bitmaps != null) {
            if (getProgress() * 0.01 * getWidth() >= getWidth()) {
                current = 3;
            } else if (getProgress() * 0.01 * getWidth() >= getWidth() * 0.75) {
                current = 2;
            } else if (getProgress() * 0.01 * getWidth() >= getWidth() * 0.25) {
                current = 1;
            } else {
                current = 0;
            }
            for (int i = 0; i < bitmaps.length; i++) {
                Bitmap bitmap = bitmaps[i];
                mSrcRect.right = bitmap.getWidth();
                mSrcRect.bottom = bitmap.getHeight();
                mDestRect.top = getHeight() / 2 - bitmap.getHeight() / 2;
                mDestRect.left = 0;
                mDestRect.right = bitmap.getWidth();
                if (i != bitmaps.length - 1) {
                    mDestRect.left = -bitmap.getWidth();
                    mDestRect.right = 0;
                }
                mDestRect.bottom = getHeight() / 2 + bitmap.getHeight() / 2;
                if (i == current) {
                    mDestRect.top -= 5;
                    mDestRect.left -= 5;
                    mDestRect.right += 5;
                    mDestRect.bottom += 5;
                }
                canvas.drawBitmap(bitmap, mSrcRect, mDestRect, mPaint);
                canvas.translate(getWidth() / 4, 0);
            }
        }
    }

    void setProgressByAnim(int progress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", 0, progress);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
