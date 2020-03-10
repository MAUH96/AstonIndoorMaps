package com.example.astonindoor.OldRoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "floor_table",foreignKeys = @ForeignKey(entity = RoomCoordinatesTable.class,
                                                    parentColumns = "roomNo",
                                                    childColumns = "roomNo"))
public class FloorTable {

    @PrimaryKey
    @ColumnInfo(name = "floorID")
    @NonNull
    private String floorID;

    @ColumnInfo(name = "roomNo")
    private int roomNo;

    public FloorTable(String floorID, int roomNo) {
        this.floorID = floorID;
        this.roomNo = roomNo;
    }

    public String getFloorID() {
        return floorID;
    }

    public int getRoomNo() {
        return roomNo;
    }

    @Override
    public String toString() {
        return "FloorMapTable{" +
                "floorID='" + floorID + '\'' +
                ", roomNo='" + roomNo + '\'' +
                '}';
    }
}


