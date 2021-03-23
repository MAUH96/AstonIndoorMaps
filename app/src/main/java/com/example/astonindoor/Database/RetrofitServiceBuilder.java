package com.example.astonindoor.Database;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitServiceBuilder {
    private final String NODE_URL = "http://10.0.2.2:3000/";
    private final OkHttpClient client = new  OkHttpClient();


    RoomsListService roomListService;
    private static RetrofitServiceBuilder instance = null;


    private RetrofitServiceBuilder() {
        GsonConverterFactory gsonFactory = GsonConverterFactory.create();
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(NODE_URL)
                .client(client)
                .addConverterFactory(gsonFactory)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        roomListService = retrofitBuilder.create(RoomsListService.class);

    }

    public static synchronized RetrofitServiceBuilder getInstance() {
        if (instance == null) {
            instance = new RetrofitServiceBuilder();
        }
        return instance;
    }

    public RoomsListService getRoomListService() {
        return roomListService;
    }

    public RoomsListService sendCurrentPosition() {
        return roomListService;
    }
}
