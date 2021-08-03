package com.example.astonindoor.Database.Respositories;

import androidx.lifecycle.MutableLiveData;

import com.example.astonindoor.Database.Models.PathModel;
import com.example.astonindoor.Database.Models.RoomModel;
import com.example.astonindoor.Database.RetrofitService.RetrofitServiceBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationRepository {
    private MutableLiveData<List<PathModel>> livePathCoordinates = new MutableLiveData<List<PathModel>>();
    private MutableLiveData<List<RoomModel>> liveRoomNode = new MutableLiveData<List<RoomModel>>();
    private List<RoomModel> roomList;
    private List<PathModel>pathCoordinates;


    /**
     * Get the optimal path calculated with Euclidean algorithm in the server.
     * @return
     */
    public MutableLiveData<List<PathModel>> getOptimalPath() {
        RetrofitServiceBuilder apiService = RetrofitServiceBuilder.getInstance();
        Call<List<PathModel>> call = apiService.getRoomListService().getXYcoordinates();
        call.enqueue(new Callback<List<PathModel>>() {
            @Override
            public void onResponse(Call<List<PathModel>> call, Response<List<PathModel>> response) {
                if(response.isSuccessful()){
                    pathCoordinates = response.body();

                    livePathCoordinates.setValue(pathCoordinates);
                }
            }
            @Override
            public void onFailure(Call<List<PathModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return livePathCoordinates;

    }

}
