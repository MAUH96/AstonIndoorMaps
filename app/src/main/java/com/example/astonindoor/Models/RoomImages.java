package com.example.astonindoor.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomImages implements Parcelable {

    private int roomNo;
    private String Image;

    public RoomImages() {
    }

    protected RoomImages(Parcel in) {
        roomNo = in.readInt();
        Image = in.readString();
    }

    public static final Creator<RoomImages> CREATOR = new Creator<RoomImages>() {
        @Override
        public RoomImages createFromParcel(Parcel in) {
            return new RoomImages(in);
        }

        @Override
        public RoomImages[] newArray(int size) {
            return new RoomImages[size];
        }
    };

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public void setImage(String image) {
        Image = image;
    }

    public RoomImages(int roomNo, String image) {
        this.roomNo = roomNo;
        Image = image;
    }


    public int getRoomNo() {
        return roomNo;
    }

    public String getImage() {
        return Image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(roomNo);
        dest.writeString(Image);
    }
}
