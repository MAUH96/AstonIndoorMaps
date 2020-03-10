package com.example.astonindoor.OldRoomDatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private FloorDao floorDao;
    private RoomCoordinatesDao roomDao;


    private LiveData<List<FloorTable>> allFloors;
    private LiveData<List<Integer>> roomNo;
    private LiveData<List<RoomCoordinatesTable>> allRooms;



    public AppRepository(Application application ){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        floorDao = appDatabase.floorMapDao();
        roomDao = appDatabase.roomCoordinatesDao();

        allFloors = floorDao.getAllFloors();
        allRooms=roomDao.getAllCoordinates();


    }


    public void update(FloorTable floorTable){
        new UpdateFloorMapAsync(floorDao).execute(floorTable);
    }
    public LiveData<List<FloorTable>> getAllFloors(){
        return allFloors;
    }
    public LiveData<List<RoomCoordinatesTable>> getAllRooms(){
        return allRooms;
    }

    public LiveData<List<Integer>> getRoombyImg(String img) {

      return  roomNo = roomDao.getCurrentPosByImage(img);

      //

    }

    private static class InsertFloorMapAsync extends AsyncTask<FloorTable,Void,Void>{

        private FloorDao floorDao;

        private InsertFloorMapAsync(FloorDao floorMapDao){
            this.floorDao=floorMapDao;
        }

        @Override
        protected Void doInBackground(FloorTable... floorTables) {
            floorDao.insert(floorTables[0]);
            return null;
        }
    }

    /**
     * AsyncTask classes are need to run operations on database on background
     * aand diplays the result on UI thread.
     *
     *  REMEMBER: REPOSITORY ONLY NEEDS OPERATIONS THAT ARE NEEDED. for example:
     *  onDelete  is not needed as it will not be performed on UI.
     *  one repo is fine for all the DAOs
     */
    private static class DeleteFloorMapAsync extends AsyncTask<FloorTable,Void,Void>{

        private FloorDao floorDao;

        private DeleteFloorMapAsync(FloorDao floorMapDao){
            this.floorDao=floorMapDao;
        }

        @Override
        protected Void doInBackground(FloorTable... floorTables) {
            floorDao.delete(floorTables[0]);
            return null;
        }
    }
//    private static class GetRoomNumberAsync extends AsyncTask<String,Void,String> {
//
//        private FloorDao floorDao;
//
//        private GetRoomNumberAsync(FloorDao floorDao){
//            this.floorDao= floorDao;
//        }
//        @Override
//        protected String doInBackground(String... room) {
//            return floorDao.getFloorByRoom(room[0]);
//
//        }
//    }


    private static class UpdateFloorMapAsync extends AsyncTask<FloorTable,Void,Void>{

        private FloorDao floorDao;

        private UpdateFloorMapAsync(FloorDao floorDao){
            this.floorDao= floorDao;
        }

        @Override
        protected Void doInBackground(FloorTable... floorTables) {
            floorDao.update(floorTables[0]);
            return null;
        }

    }



}




