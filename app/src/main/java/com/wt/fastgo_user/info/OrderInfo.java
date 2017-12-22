package com.wt.fastgo_user.info;

import android.os.Parcel;
import android.os.Parcelable;


public class OrderInfo implements Parcelable {
    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel in) {
            return new OrderInfo(in);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
    private boolean check;
    private int id;
    private Info info;

    protected OrderInfo(Parcel in) {
        check = in.readByte() != 0;
        id = in.readInt();
    }

    public OrderInfo() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (check ? 1 : 0));
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public static class Info implements Parcelable {
        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel in) {
                return new Info(in);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
            }
        };

        public Info() {
        }

        protected Info(Parcel in) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

}
