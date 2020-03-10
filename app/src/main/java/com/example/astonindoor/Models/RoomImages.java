package com.example.astonindoor.Models;

public class RoomImages {

    private int roomNo;
    private String Image;

    public RoomImages() {
    }

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

}
