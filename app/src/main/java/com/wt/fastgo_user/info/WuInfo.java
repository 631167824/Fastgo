package com.wt.fastgo_user.info;


import android.os.Parcel;
import android.os.Parcelable;

public class WuInfo implements Parcelable {
    public static final Creator<WuInfo> CREATOR = new Creator<WuInfo>() {
        @Override
        public WuInfo createFromParcel(Parcel in) {
            return new WuInfo(in);
        }

        @Override
        public WuInfo[] newArray(int size) {
            return new WuInfo[size];
        }
    };
    private int id;

    public WuInfo() {
    }

    protected WuInfo(Parcel in) {
        id = in.readInt();
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
        dest.writeInt(id);
    }
}
