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
public interface FloorDao {

    @Insert
    void insert(FloorTable floor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FloorTable> floors);

    @Update
    void update(FloorTable floor);

    @Delete
    void delete(FloorTable floor);

    @Query("SELECT * FROM floor_table")
        LiveData<List<FloorTable>> getAllFloors();

   @Query("SELECT floorID FROM floor_table WHERE roomNo = :room")
   LiveData<List<String>> getFloorByRoom(String room);


}
