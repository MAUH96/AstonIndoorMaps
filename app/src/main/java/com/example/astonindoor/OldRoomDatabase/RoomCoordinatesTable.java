package com.example.astonindoor.OldRoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName="rooms_coordinates_table",indices = {@Index(value = {"roomNo"}, unique = true)})
public class RoomCoordinatesTable {


    @PrimaryKey
    @ColumnInfo(name = "roomNo")

    private int roomNo;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "roomImages")
    private String roomImages;


    public RoomCoordinatesTable (int roomNo, String roomImages,  String longitude, String latitude) {

        this.roomNo = roomNo;
        this.longitude=longitude;
        this.latitude=latitude;
        this.roomImages=roomImages;
    }
    @Ignore
    public RoomCoordinatesTable () {
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


    public String getRoomImages() {
        return roomImages;
    }

}


