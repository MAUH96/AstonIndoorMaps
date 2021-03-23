package com.example.astonindoor.Database;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.astonindoor.Models.FloorModel;
import com.example.astonindoor.Models.RoomImages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppViewModel extends ViewModel {

    private static String TAG = "ListViewModel";
    private RoomImages roomImg = new RoomImages();
    private FloorModel floorNum;


    private static final DatabaseReference dataRef =
            FirebaseDatabase.getInstance().getReference(); // getting data base refs
    private final DatabaseLiveRepository mLiveData = new DatabaseLiveRepository(dataRef);
    private final  LiveData<List<FloorModel>> floorNumbersLiveData = Transformations.map(mLiveData, new FloorDeserializer());

    private List<FloorModel> flist = new ArrayList<>();

    /**
     * function<i,o> where "i" is the input and "o" is the output.
     * datasnapshot is the input which is being used to populate the mList so it can
     * return a reference of RoomImages from the database
     *
     * this class is being used from Transformation.map to assign it to a livedata list of RoomImages
     */

    private class FloorDeserializer implements Function<DataSnapshot, List<FloorModel>> {

        @Override
        public List<FloorModel> apply(DataSnapshot dataSnapshot) {
            flist.clear();
            for(DataSnapshot snap : dataSnapshot.getChildren()){

                floorNum = snap.getValue(FloorModel.class);

                flist.add(floorNum);
            }
            return flist;
        }
    }

    @NonNull
    public LiveData<List<FloorModel>> getLiveFloorNum(){

        return floorNumbersLiveData;
    }

//------------------------These are other database structres (like room images) that I might need or not--------------------------------------

    //private final LiveData<List<RoomImages>> roomImagesLiveData =   Transformations.map(mLiveData, new RoomImgDeserializer());
    // private final  LiveData<List<FloorNode>> floorNodesLiveData = Transformations.map(mLiveData, new FloorNodesDeserializer());
    //private List<RoomImages> rList = new ArrayList<>();
    //private List<FloorNode> nList = new ArrayList<>();


//    private class RoomImgDeserializer implements Function<DataSnapshot, List<RoomImages>> {
//
//        @Override
//        public List<RoomImages> apply(DataSnapshot dataSnapshot) {
//            rList.clear();
//            for(DataSnapshot snap : dataSnapshot.getChildren()){
//
//                roomImg = snap.getValue(RoomImages.class);
//                rList.add(roomImg);
//            }
//            return rList;
//        }
//    }
//
//    @NonNull
//    public LiveData<List<RoomImages>> getLiveRoomImages(){
//
//        return roomImagesLiveData;
//    }




//
//    private class FloorNodesDeserializer implements Function<DataSnapshot, List<FloorNode>> {
//
//        @Override
//        public List<FloorNode> apply(DataSnapshot dataSnapshot) {
//            flist.clear();
//            for(DataSnapshot snap : dataSnapshot.getChildren()){
//                for(DataSnapshot nodeSnap : snap.getChildren()){
//                    nodeName = snap.getValue(FloorNode.class);
//                    nList.add(nodeName);
//                }
//            }
//            return nList;
//        }
//    }

//    @NonNull
//    public LiveData<List<FloorNode>> getFloorNode(){
//
//        return floorNodesLiveData;
//    }

}
