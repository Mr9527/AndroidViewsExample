package com.qhfax.androidviewsexample.canvas;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;

/**
 * @author chenzhaojun
 * @date 2017/7/10
 * @description
 */

public class PageData implements Parcelable {

    private String name;
    @LayoutRes
    private int id;

    public PageData() {
    }

    public PageData(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    protected PageData(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<PageData> CREATOR = new Parcelable.Creator<PageData>() {
        @Override
        public PageData createFromParcel(Parcel source) {
            return new PageData(source);
        }

        @Override
        public PageData[] newArray(int size) {
            return new PageData[size];
        }
    };
}
