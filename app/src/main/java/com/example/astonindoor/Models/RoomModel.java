package com.example.astonindoor.Models;

public class RoomModel {

    private int roomNo;
    private String longitude, latitude;

    public RoomModel(int roomNo, String longitude, String latitude){
        this.roomNo=roomNo;

        this.longitude=longitude;
        this.latitude=latitude;
    }

    public int getRoomNo() {
        return roomNo;
    }


    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
