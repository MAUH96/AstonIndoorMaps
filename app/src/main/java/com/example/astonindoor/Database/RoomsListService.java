package com.example.astonindoor.Database;

import com.example.astonindoor.Models.FloorModel;
import com.example.astonindoor.Models.RoomModel;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RoomsListService {
    @GET("rooms")
    Call<List<FloorModel>> getRoomList();

    @POST("currentPosition")
    Call<String>sendCurrentPos(@Body HashMap<String,String> currentPos);
}
