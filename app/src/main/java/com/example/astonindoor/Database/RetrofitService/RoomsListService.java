package com.example.astonindoor.Database.RetrofitService;

import com.example.astonindoor.Models.RoomModel;
import com.example.astonindoor.Models.RoomWrapper;
import com.example.astonindoor.Models.ValidationClass;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RoomsListService {
    @GET("rooms")
    Call<List<RoomModel>> getRoomList();

    @GET("isSelected")
    Call<String> getValidation();

    @POST("destinationRoom")
    Call<String> sendDestinationRoom(@Body HashMap<String, String> currentPos);

    @POST("currentPosition")
    Call<String> sendCurrentPosition(@Body HashMap<String, String> currentPos);
}
