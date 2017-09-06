package com.qhfax.androidviewsexample.canvas.pie;

/**
 * @author chenzhaojun
 * @date 2017/7/10
 * @description
 */

public class PieData {

    private String name;

    private float value;

    private int color;

    private float percentage;

    private float angle;

    private boolean isDrawLine;


    public PieData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public boolean isDrawLine() {
        return isDrawLine;
    }

    public void setDrawLine(boolean drawLine) {
        isDrawLine = drawLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
