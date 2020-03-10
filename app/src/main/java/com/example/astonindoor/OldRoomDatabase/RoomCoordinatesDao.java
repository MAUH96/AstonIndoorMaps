package com.example.astonindoor.OldRoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomCoordinatesDao {

    @Insert
    void insert(RoomCoordinatesTable roomsCoordinates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RoomCoordinatesTable> roomsCoordinates);

    @Update
    void update(RoomCoordinatesTable roomsCoordinates);

    @Delete
    void delete(RoomCoordinatesTable roomsCoordinates);

    @Query("SELECT * FROM  rooms_coordinates_table ORDER BY roomNo ASC")
    LiveData<List<RoomCoordinatesTable>> getAllCoordinates();

    @Query("SELECT longitude FROM rooms_coordinates_table WHERE roomNo = :room")
    String getLongByRoom(int room);

    @Query("SELECT latitude FROM rooms_coordinates_table WHERE roomNo = :room")
    String getLatByRoom(int room);

    @Query("SELECT roomNo FROM rooms_coordinates_table WHERE roomImages = :currentImage")
    LiveData<List<Integer>> getCurrentPosByImage(String currentImage);



}
