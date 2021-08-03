package com.example.astonindoor.Database.RetrofitService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitServiceBuilder {
    private final String NODE_URL = "http://10.0.2.2:3000/";
    private final OkHttpClient client;
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();




    RoomsListService roomListService;
    private static RetrofitServiceBuilder instance = null;


    private RetrofitServiceBuilder() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging).build();
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


}
