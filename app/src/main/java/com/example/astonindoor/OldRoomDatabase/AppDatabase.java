package com.example.astonindoor.OldRoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * This class is a ViewModel, a bridge between model and view.
 * Its purpose is to hold and prepare all the data for the UI so we don't have to put or store anything
 * in our mainActivity class (MapsActivity.java).
 *
 *version n° must be incremented if the database (entities) are modified
 */
@Database(entities = {FloorTable.class, RoomCoordinatesTable.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract FloorDao floorMapDao();
    public abstract RoomCoordinatesDao roomCoordinatesDao();
    private static BufferedReader floorReader;
    private static BufferedReader roomReader ;
    private static final String Tag="MyData";

    /**
     * Synchronized: only one thread/ execution can access the method a time
     * @param context
     * @return  AppDatabse Instance
     *
     * Singletong pattern use: Database only instantiated if is null,
     * else, return existing instance.
     */
    public synchronized static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                    AppDatabase.class, "app_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCordCallback).build();
        }
        return instance;
    }


    static final Migration MIGRATION_1_2 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class,
                "floorMap_table").fallbackToDestructiveMigration()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                         final List<FloorTable> floorMaps = new ArrayList<>();
                         final List<RoomCoordinatesTable> roomTable = new ArrayList<>();
                        try {

                            /**
                             * Populating floorTable database through a csv file in assets
                             */
                            floorReader = new BufferedReader(new InputStreamReader(context.getAssets().open("floorMapFile.csv")));
                            floorReader.skip(1); // skip header
                            //read lines from csv

                            String floorRecord ="";
                            String floorID, mMaps;

                            while((floorRecord = floorReader.readLine())!=null){
                                String[] tokens = floorRecord.split(",");

                                floorID = tokens[0];
                                mMaps = tokens[1];

                                FloorTable floorMap = new FloorTable(floorID, Integer.parseInt(mMaps));
                                floorMaps.add(floorMap);

                            }

                            /*
                             * Populating RoomCoordinatesTable through a csv file in assests
                             */
                            roomReader = new BufferedReader(new InputStreamReader(context.getAssets().open("roomCordFile.csv")));

                            roomReader.skip(1);
                            //read lines from csv

                            String roomRecord="";
                            String roomImg,longi,lati,roomStr;
                            int roomNo;
                            //reads each row untils is null
                            while((roomRecord = roomReader.readLine())!=null) {
                                String[] tokens = roomRecord.split(","); //value splitted by ","

                                roomStr = tokens[0];
                                roomImg = tokens[1];
                                longi = tokens[2];
                                lati = tokens[3];



                                RoomCoordinatesTable roomDB = new RoomCoordinatesTable(Integer.parseInt(roomStr),roomImg,longi,lati);
                                roomTable.add(roomDB);

                            }

                        } catch (IOException e) {

                            e.printStackTrace();
                        }



                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).floorMapDao().insertAll(floorMaps);
                                getInstance(context).roomCoordinatesDao().insertAll(roomTable);



                            }
                        });
                    }
                })
                .build();


    }


//    private static RoomDatabase.Callback roomCordCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
//       // private RoomCoordinatesDao roomCordDao;
//        private FloorDao floorMapDao;
//
//
//        private PopulateDbAsyncTask(AppDatabase db){
//           // roomCordDao = db.roomCoordinatesDao();
//            floorMapDao = db.floorMapDao();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//
//           // final List<RoomCoordinatesTable> roomTable = new ArrayList<>();
//            final List<FloorTable> floorTable = new ArrayList<>();
//            try {
//
//                String fileFloor = "../res/raw/floorMapFile.csv";
//                InputStream inFloor = getClass().getClassLoader().getResourceAsStream(fileFloor);
//                floorReader = new BufferedReader(new InputStreamReader(inFloor,"UTF-8"));
//                            floorReader.skip(1); // skip header
//                           //read lines from csv
//
//                            String floorRecord ="";
//                            String floorID, mMaps;
//
//                            while((floorRecord = floorReader.readLine())!=null) {
//                                String[] tokens = floorRecord.split(",");
//
//                                floorID = tokens[0];
//                                mMaps = tokens[1];
//
//                                FloorTable floorMap = new FloorTable(floorID, Integer.parseInt(mMaps));
//                                floorTable.add(floorMap);
//                            }
//                /**
//                 * Populating RoomCoordinatesTable through a csv file in assests
//                 *
//                 */
//
////                String fileRoom = "../res/raw/roomCordFile.csv";
////                InputStream inRoom = getClass().getClassLoader().getResourceAsStream(fileRoom);
////                roomReader = new BufferedReader(new InputStreamReader(inRoom, "UTF-8"));
////                roomReader.skip(1);//jump header
////                //read lines from csv
////
////                String roomRecord="";
////                String roomImg,longi,lati,roomStr;
////                int roomNo;
////                //reads each row untils is null
////                while((roomRecord = roomReader.readLine())!=null) {
////                    String[] tokens = roomRecord.split(","); //value splitted by ","
////
////                    roomStr = tokens[0];//first column
////                    roomImg = tokens[1];//second e
////                    longi = tokens[2];
////                    lati = tokens[3];
////
////                    RoomCoordinatesTable roomDB = new RoomCoordinatesTable(Integer.parseInt(roomStr),roomImg,longi,lati);
////                    roomTable.add(roomDB);
////                }
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//            //roomCordDao.insertAll(roomTable);
//            floorMapDao.insertAll(floorTable);
//
//            return null;
//        }
//
//    }
//
//    public static void destroyInstance(){
//        if (instance.isOpen()) instance.close();
//        instance = null;
//    }



}
