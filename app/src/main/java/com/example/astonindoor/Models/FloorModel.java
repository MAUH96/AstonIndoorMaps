package com.example.astonindoor.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FloorModel {

    @SerializedName("numRoom")
    @Expose
    private String numRoom;

    @SerializedName("roomName")
    @Expose
    private String roomName;

    public String getNumRoom() {
        return numRoom;
    }
    public String getRoomName(){
        return roomName;
    }
    public void setRoomName(String roomName){
        this.roomName=roomName;
    }

    public void setNumRoom(String numRoom) {
        this.numRoom = numRoom;
    }


}
