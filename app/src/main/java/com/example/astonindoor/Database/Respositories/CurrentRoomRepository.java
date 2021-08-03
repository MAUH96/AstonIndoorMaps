package com.example.astonindoor.Database.Respositories;

import androidx.lifecycle.MutableLiveData;

import com.example.astonindoor.Database.RetrofitService.RetrofitServiceBuilder;
import com.example.astonindoor.Database.Models.RoomModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRoomRepository {
    // private RoomModel users = new ArrayList<>();
    private MutableLiveData<List<String>> liveRoomNum = new MutableLiveData<List<String>>();
    private MutableLiveData<List<String>> isValid= new MutableLiveData<>();
    private MutableLiveData<String> validationValue= new MutableLiveData<>();



    public MutableLiveData<List<String>> getLiveRoomNum() {
        RetrofitServiceBuilder apiService = RetrofitServiceBuilder.getInstance();
        Call<List<RoomModel>> call = apiService.getRoomListService().getRoomList();
        List<String> roomNames = new ArrayList<>();

        call.enqueue(new Callback<List<RoomModel>>() {
            @Override
            public void onResponse(Call<List<RoomModel>> call, Response<List<RoomModel>> response) {
                List<RoomModel> roomList = response.body();
                for (RoomModel s : roomList) {

                    roomNames.add(s.getNumRoom());
                }



                liveRoomNum.setValue(roomNames);

            }

            @Override
            public void onFailure(Call<List<RoomModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return liveRoomNum;

    }

    public void sendToServer(String selectedRoom) {

        HashMap<String, String> currentRoom = new HashMap<>();
        currentRoom.put("numRoom", "" + selectedRoom);
        System.out.println(currentRoom);
        RetrofitServiceBuilder postToServer = RetrofitServiceBuilder.getInstance();
        Call<String> call = postToServer.getRoomListService().sendCurrentPosition(currentRoom);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    System.out.println("sent to the server " + response.body());
                } else if (response.body() != null) {
                    System.out.println("value null");
                } else {
                    System.out.println("not successful");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }



    public MutableLiveData<List<String>>isSelected() {
        RetrofitServiceBuilder apiService = RetrofitServiceBuilder.getInstance();
        Call<String> call = apiService.getRoomListService().getValidation();
        List<String> temp = new ArrayList<>();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                temp.add(res);
                isValid.setValue(temp);


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


        return isValid;
    }




//    public void isSelected(String validateSelection) {
//        RetrofitServiceBuilder apiService = RetrofitServiceBuilder.getInstance();
//        HashMap<String, String> jsonFormat= new HashMap<>();
//        jsonFormat.put("validation", "" + validateSelection );
//        Call<String> call = apiService.getRoomListService().getValidation(jsonFormat);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//
//                    System.out.println("sent to the server " + response.body());
//                } else if (response.body() != null) {
//                    System.out.println("value null");
//                } else {
//                    System.out.println("not successful");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });


    }
