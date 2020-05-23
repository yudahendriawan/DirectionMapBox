package com.yudahendriawan.ProjectTugasAkhir.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wisata implements Parcelable {
    @Expose
    @SerializedName("number")
    private int number;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("openTime")
    private String openTime;

    @Expose
    @SerializedName("fee")
    private String fee;

    @Expose
    @SerializedName("imgUrl")
    private String imgUrl;

    @Expose
    @SerializedName("summary")
    private String summary;

    protected Wisata(Parcel in) {
        number = in.readInt();
        name = in.readString();
        address = in.readString();
        openTime = in.readString();
        fee = in.readString();
        imgUrl = in.readString();
        summary = in.readString();
    }

    public static final Creator<Wisata> CREATOR = new Creator<Wisata>() {
        @Override
        public Wisata createFromParcel(Parcel in) {
            return new Wisata(in);
        }

        @Override
        public Wisata[] newArray(int size) {
            return new Wisata[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(openTime);
        dest.writeString(fee);
        dest.writeString(imgUrl);
        dest.writeString(summary);
    }
}
