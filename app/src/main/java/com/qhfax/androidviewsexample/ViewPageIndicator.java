package com.qhfax.androidviewsexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author chenzhaojun
 * @date 2017/4/12
 * @description
 */

public class ViewPageIndicator extends LinearLayout {

    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final int TAB_DEFAULT_COUNT = 4;
    private static final float RADIO_TRIANGEL = 1.0f / 6;
    private int mTabVisibleCount = TAB_DEFAULT_COUNT;
    private List<String> mTabTitles;
    public ViewPager mViewPager;
    private int normal_color = Color.parseColor("#fcd5b7");
    private int select_color = Color.parseColor("#FFFFFF");
    private int mInitTranslationX;
    private float mTranslationX;
    private Paint mLinePaint;

    public static final int IMAGE_UNSELECTED_ARRAY[] = {R.drawable.vip_regular_members_n, R.drawable.vip_silver_member_n, R.drawable.vip_gold_membership_n, R.drawable.vip_diamond_membership_n};
    public static final int IMAGE_SELECTED_ARRAY[] = {R.drawable.vip_regular_members_one, R.drawable.vip_silver_member_two, R.drawable.vip_gold_membership_three, R.drawable.vip_diamond_membership_four};

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public ViewPageIndicator(Context context) {
        this(context, null);
    }

    public ViewPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFFFFFFF"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.parseColor("#F48210"));
        mLinePaint.setStyle(Paint.Style.FILL);
        mTabTitles = new ArrayList<>(4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGEL);
        mTriangleHeight = (mTriangleWidth / 2);
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;
        initTriangle();
    }

    private void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        canvas.drawRect(new RectF(0, getHeight() / 3, getWidth(), getHeight() / 3 + SizeUtils.dp2px(getContext(), 5)), mLinePaint);
        super.dispatchDraw(canvas);
    }

    public void setWithViewPager(@NonNull ViewPager viewPager) {
        if (viewPager.getAdapter() != null && viewPager.getAdapter().getCount() > 4) {
            throw new RuntimeException("maxsize is four  ");
        }
        if (mOnPageChangeListener != null) {
            viewPager.removeOnPageChangeListener(mOnPageChangeListener);
        }
        mViewPager = viewPager;
        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                resetViewStyle();
                highLightViewStyle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        if (viewPager.getAdapter().getCount() > 0) {
            mTabTitles.clear();
            this.removeAllViews();
            for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
                mTabTitles.add(viewPager.getAdapter().getPageTitle(i).toString());
                addView(generateTextView(mTabTitles.get(i), i));
            }
            setItemClickEvent();
        }
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        highLightViewStyle(0);
    }

    private View generateTextView(String title, int i) {
        RelativeLayout group = new RelativeLayout(getContext());
        TextView label = new TextView(getContext());
        ImageView imageView = new ImageView(getContext());
        LayoutParams tabLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams labelParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tabLayoutParams.width = getScreenWidth() / mTabVisibleCount;
        labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        labelParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        group.setLayoutParams(tabLayoutParams);
        //空出区域给三角标示显示
        label.setPadding(0, 0, 0, SizeUtils.dp2px(getContext(), 15));
        label.setGravity(Gravity.CENTER);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        label.setTextColor(normal_color);
        label.setLayoutParams(labelParams);
        imageView.setLayoutParams(imageParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(IMAGE_UNSELECTED_ARRAY[i]);
        //设置一定的偏移量
        imageView.setPadding(0, 0, 0, SizeUtils.dp2px(getContext(), 24));
        TextPaint tp = label.getPaint();
        tp.setFakeBoldText(true);
        label.setText(title);
        group.addView(imageView);
        group.addView(label);
        return group;
    }

    private void highLightViewStyle(int position) {
        RelativeLayout tab = (RelativeLayout) getChildAt(position);
        ImageView img = (ImageView) tab.getChildAt(0);
        TextView label = (TextView) tab.getChildAt(1);
        label.setTextColor(select_color);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        img.setImageResource(IMAGE_SELECTED_ARRAY[position]);

    }

    private void resetViewStyle() {
        for (int i = 0; i < getChildCount(); i++) {
            RelativeLayout tab = (RelativeLayout) getChildAt(i);
            ImageView img = (ImageView) tab.getChildAt(0);
            TextView label = (TextView) tab.getChildAt(1);
            label.setTextColor(normal_color);
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            img.setImageResource(IMAGE_UNSELECTED_ARRAY[i]);
        }
    }

    /**
     * 移动三角下标
     * @param position 当前项
     * @param positionOffset 偏移量
     */
    private void scroll(int position, float positionOffset) {
        mTranslationX = getWidth() / mTabVisibleCount * (position + positionOffset);
        int tabWidth = getWidth() / mTabVisibleCount;
        if (position > (mTabVisibleCount - 2) && positionOffset > 0 && getChildCount() > mTabVisibleCount) {
            if (mTabVisibleCount != 1) {
                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth + (int) (tabWidth * positionOffset), 0);
            } else {
                this.scrollTo(position * tabWidth + (int) (tabWidth * positionOffset), 0);
            }
        }
        invalidate();
    }

    /**
     * 获得屏幕的宽度
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent() {
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            if (mViewPager != null) {
                final int j = i;
                View view = getChildAt(i);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(j);
                    }
                });
            }
        }

    }

}
