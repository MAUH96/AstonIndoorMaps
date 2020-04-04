package com.example.astonindoor;

import androidx.lifecycle.Observer;

import com.example.astonindoor.Database.AppViewModel;
import com.example.astonindoor.Models.RoomImages;

import java.util.ArrayList;
import java.util.List;

public class roomList {

    List<List<String>> roomImages = new ArrayList<>();

    public roomList(){



    }
    //
//    public ArrayList<RoomImages>getAllImg(MapsActivity map, AppViewModel viewModel ){
//
//        final ArrayList<String>imagesDb = new ArrayList<>();
//        viewModel.getLiveRoomImages().observe(map,new Observer<List<RoomImages>>() {
//            @Override
//            public void onChanged(List<RoomImages> dbRoomImg) {
//
//                imagesDb.add(String.valueOf(dbRoomImg));
//
//            }
//        });
//               return imagesDb;
//    }



}

