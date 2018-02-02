package com.ayfp.anyuanwisdom.view.report.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:: wangjianchi
 * @time: 2017/11/30  16:18.
 * @description:
 */

public class ReportImageBean implements Parcelable {
    private int type = 1;
    private boolean delete;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    //1 上传照片 2 file  3 url

    private String imageFile;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }


    public ReportImageBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeByte(this.delete ? (byte) 1 : (byte) 0);
        dest.writeString(this.imageFile);
    }

    protected ReportImageBean(Parcel in) {
        this.type = in.readInt();
        this.delete = in.readByte() != 0;
        this.imageFile = in.readString();
    }

    public static final Creator<ReportImageBean> CREATOR = new Creator<ReportImageBean>() {
        @Override
        public ReportImageBean createFromParcel(Parcel source) {
            return new ReportImageBean(source);
        }

        @Override
        public ReportImageBean[] newArray(int size) {
            return new ReportImageBean[size];
        }
    };
}
