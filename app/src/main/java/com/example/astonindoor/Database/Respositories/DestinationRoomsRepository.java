package com.example.astonindoor.Database.Respositories;

import androidx.lifecycle.MutableLiveData;


import com.example.astonindoor.Database.RetrofitService.RetrofitServiceBuilder;
import com.example.astonindoor.Models.RoomModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationRoomsRepository {
    // private RoomModel users = new ArrayList<>();
    private MutableLiveData<List<String>> liveRoomNum = new MutableLiveData<List<String>>();
    private MutableLiveData<List<RoomModel>> liveRoomNode = new MutableLiveData<List<RoomModel>>();
    private List<RoomModel> roomList;


    public MutableLiveData<List<RoomModel>> getLiveRoomNode() {
        RetrofitServiceBuilder apiService = RetrofitServiceBuilder.getInstance();
        Call<List<RoomModel>> call = apiService.getRoomListService().getRoomList();

        call.enqueue(new Callback<List<RoomModel>>() {
            @Override
            public void onResponse(Call<List<RoomModel>> call, Response<List<RoomModel>> response) {
                roomList = response.body();

                liveRoomNode.setValue(roomList);

            }

            @Override
            public void onFailure(Call<List<RoomModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return liveRoomNode;

    }

    public void sendToServer(int position) {

        String destinationRoomNum = roomList.get(position).getNumRoom();
        HashMap<String, String> destinationRoom = new HashMap<>();
        destinationRoom.put("numRoom", "" + destinationRoomNum);
        System.out.println(destinationRoom);
        RetrofitServiceBuilder postToServer = RetrofitServiceBuilder.getInstance();
        Call<String> call = postToServer.getRoomListService().sendDestinationRoom(destinationRoom);
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
}