package com.example.astonindoor.Database;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.astonindoor.Models.RoomImages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppViewModel extends ViewModel {

    private static String TAG = "ListViewModel";
    private RoomImages roomImg = new RoomImages();

    private static final DatabaseReference dataRef =
            FirebaseDatabase.getInstance().getReference().child("RoomCordinates");
    private final FirebaseQueryLiveData mLiveData = new FirebaseQueryLiveData(dataRef);
    private final LiveData<List<RoomImages>> roomImagesLiveData =   Transformations.map(mLiveData, new Deserializer());

    private List<RoomImages> mList = new ArrayList<>();


    private class Deserializer implements Function<DataSnapshot, List<RoomImages>> {

        @Override
        public List<RoomImages> apply(DataSnapshot dataSnapshot) {
            mList.clear();
            for(DataSnapshot snap : dataSnapshot.getChildren()){

                roomImg = snap.getValue(RoomImages.class);

                mList.add(roomImg);

            }
            return mList;
        }
    }

    @NonNull
    public LiveData<List<RoomImages>> getLiveRoomImages(){

        return roomImagesLiveData;
    }

}
