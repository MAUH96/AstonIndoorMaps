package com.example.astonindoor.Database.Respositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.astonindoor.Database.RetrofitService.RetrofitServiceBuilder;
import com.example.astonindoor.Database.Models.PathModel;
import com.example.astonindoor.Database.Models.RoomModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationRoomsRepository {
    // private RoomModel users = new ArrayList<>();
    private MutableLiveData<List<PathModel>> livePathCoordinates = new MutableLiveData<List<PathModel>>();
    private MutableLiveData<List<RoomModel>> liveRoomNode = new MutableLiveData<List<RoomModel>>();
    private List<RoomModel> roomList;
    private List<PathModel>pathCoordinates;

    /**
     * Get the list of destination rooms from the server
     * @return
     */
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

    /**
     * send the destination room chosen back to the server
     * @param position
     */

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