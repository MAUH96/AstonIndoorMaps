package com.example.astonindoor.Database.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PathModel {

    @SerializedName("xDBCoordinates")
    @Expose
    private List<String> xDBCoordinates = null;
    @SerializedName("yDBCoordinates")
    @Expose
    private List<String> yDBCoordinates = null;

    @SerializedName("roomNum")
    @Expose
    private final List<String> roomNum = null;



    public List<String> getxDBCoordinates() {
        return xDBCoordinates;
    }

    public void setxDBCoordinates(List<String> xDBCoordinates) {
        this.xDBCoordinates = xDBCoordinates;
    }

    public List<String> getyDBCoordinates() {
        return yDBCoordinates;
    }

    public void setyDBCoordinates(List<String> yDBCoordinates) {
        this.yDBCoordinates = yDBCoordinates;
    }

    public List<String> getRoomNum() {
        return roomNum;
    }
}
