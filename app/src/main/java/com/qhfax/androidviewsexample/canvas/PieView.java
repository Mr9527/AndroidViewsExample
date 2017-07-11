package com.qhfax.androidviewsexample.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzhaojun
 * @date 2017/7/10
 * @description
 */

public class PieView extends View {

    private List<PieData> mPies;

    private int mHeight;

    private int mWidth;

    private Paint mPaint;

    private RectF mRectF;

    private float posX = 0.0f;
    private float posY = 0.0f;

    private float radius;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new Paint();
        mRectF = new RectF();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        radius = Math.min(mWidth, mHeight) / 4;
        testInitData();
    }

    private void testInitData() {
        // TODO: 2017/7/10 测试数据
        List<PieData> list = new ArrayList<>();
        list.add(new PieData("java", 30832));
        list.add(new PieData("kotlin", 2782));
        list.add(new PieData("C/C++", 25803));
        list.add(new PieData("JavaScript", 28803));
        list.add(new PieData("Python", 20000));
        setData(list);
    }

    public void setData(List<PieData> pieDatas) {
        if (pieDatas == null || pieDatas.size() == 0) {
            return;
        }
        float sumValue = 0;
        for (PieData pieData : pieDatas) {
            sumValue += pieData.getValue();
        }
        for (PieData pieData : pieDatas) {
            float percentage = pieData.getValue() / sumValue;
            float angle = percentage * 360;
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
            if (pieData.getColor() == 0) {
                pieData.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            }
        }
        mPies = pieDatas;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPies == null) {
            return;
        }
        canvas.translate(mWidth / 4, mHeight / 4);
        float startAngle = 0;
        for (int i = 0; i < mPies.size(); i++) {
            PieData pieData = mPies.get(i);
            calcArcEndPointXY(getWidth() / 4, getHeight() / 4, radius / 55, startAngle + pieData.getAngle() / 2);
            mRectF.top = posY - radius;
            mRectF.left = posX - radius;
            mRectF.right = posX + radius;
            mRectF.bottom = posY + radius;
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(mRectF, startAngle, pieData.getAngle(), true, mPaint);
            startAngle += pieData.getAngle();
        }
    }

    //依圆心坐标，半径，扇形角度，计算出扇形终射线与圆弧交叉点的xy坐标
    public void calcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle) {

        //将角度转换为弧度
        float arcAngle = (float) (Math.PI * cirAngle / 180.0);
        if (cirAngle < 90) {
            posX = cirX + (float) (Math.cos(arcAngle)) * radius;
            posY = cirY + (float) (Math.sin(arcAngle)) * radius;
        } else if (cirAngle == 90) {
            posX = cirX;
            posY = cirY + radius;
        } else if (cirAngle > 90 && cirAngle < 180) {
            arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
            posX = cirX - (float) (Math.cos(arcAngle)) * radius;
            posY = cirY + (float) (Math.sin(arcAngle)) * radius;
        } else if (cirAngle == 180) {
            posX = cirX - radius;
            posY = cirY;
        } else if (cirAngle > 180 && cirAngle < 270) {
            arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
            posX = cirX - (float) (Math.cos(arcAngle)) * radius;
            posY = cirY - (float) (Math.sin(arcAngle)) * radius;
        } else if (cirAngle == 270) {
            posX = cirX;
            posY = cirY - radius;
        } else {
            arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
            posX = cirX + (float) (Math.cos(arcAngle)) * radius;
            posY = cirY - (float) (Math.sin(arcAngle)) * radius;
        }
    }
}
