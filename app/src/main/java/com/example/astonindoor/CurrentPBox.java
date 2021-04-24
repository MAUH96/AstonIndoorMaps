package com.example.astonindoor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.astonindoor.Database.RetrofitServiceBuilder;
import com.example.astonindoor.Models.FloorModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentPBox extends Fragment {
    private List<FloorModel>roomList;
    Call<List<FloorModel>>serverReqsCall;
    AutoCompleteTextView currentPos;
    private Call<String> postToServer;

    public CurrentPBox(){
        super(R.layout.current_pos_box);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.current_pos_box,container,false);
        currentPos = v.findViewById(R.id.currentPostion);
        serverReqsCall = RetrofitServiceBuilder.getInstance().getRoomListService().getRoomList();
        serverReqsCall.enqueue(new Callback<List<FloorModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<FloorModel>> call, @NotNull Response<List<FloorModel>> response) {
                System.out.println("response code: " + response.code());
                ArrayList<String> roomNames = new ArrayList<>();
                if (response.code() == 200) {

                    roomList = response.body();
                    for (FloorModel s : roomList) {
                        roomNames.add(s.getNumRoom());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_dropdown_item_1line, roomNames);
                    currentPos.setThreshold(1);//will start working from first character
                    currentPos.setAdapter(adapter);//setting the adapter data into the
                    currentPos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String currentPosition = parent.getItemAtPosition(position).toString();
                            sendToServer(currentPosition);
                            // create Toast with user selected value
                            //Toast.makeText(this.getActivity(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                            System.out.println("CurrentPositon " + currentPosition);
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<List<FloorModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return v;
    }

    public void sendToServer(String selectedRoom){

        HashMap<String,String> destinationRoom = new HashMap<>();
        destinationRoom.put("numRoom",""+selectedRoom);
        System.out.println(destinationRoom);
        postToServer = RetrofitServiceBuilder.getInstance().getRoomListService()
                .sendCurrentPosition(destinationRoom);
        postToServer.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {

                    System.out.println("sent to the server " + response.body());
                }else if(response.body()!=null){
                    System.out.println("value null");
                }else{
                    System.out.println("not successful");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
