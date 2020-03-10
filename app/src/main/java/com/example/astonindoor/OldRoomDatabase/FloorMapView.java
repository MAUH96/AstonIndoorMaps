package com.example.astonindoor.OldRoomDatabase;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class FloorMapView extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<FloorTable>> allFloors;
    private LiveData<List<RoomCoordinatesTable>> allRooms;
    private LiveData<List<Integer>> roomsNo;
    private String Floor;
    private Context context;


    public FloorMapView(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        allFloors = repository.getAllFloors();
        allRooms = repository.getAllRooms();



    }

    public void update(FloorTable floorMap){
        repository.update(floorMap);
    }

    public LiveData<List<FloorTable>> getAllFloors(){
        return allFloors;
    }

    public LiveData<List<RoomCoordinatesTable>> getAllRooms(){
        return allRooms;
    }

    public LiveData<List<Integer>> getRoomByImg(String img){
        return roomsNo = repository.getRoombyImg(img);
    }

    public void printFloor(){


        System.out.println(allFloors.getValue());
    }






}
