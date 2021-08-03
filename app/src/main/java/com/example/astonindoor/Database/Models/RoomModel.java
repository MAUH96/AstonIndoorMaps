package com.example.astonindoor.Database.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomModel {

    @SerializedName("numRoom")
    @Expose
    private String numRoom;

    @SerializedName("roomName")
    @Expose
    private String roomName;

//    @SerializedName("x")
//    private String x;
//    @SerializedName("y")
//    private String y;

    public String getNumRoom() {
        return numRoom;
    }

    public String getRoomName() {
        return roomName;
    }

//    public String getX() {
//        return x;
//    }
//
//    public String getY() {
//        return y;
//    }

}
