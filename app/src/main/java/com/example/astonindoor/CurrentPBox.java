package com.example.astonindoor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.astonindoor.Database.ViewModel.CurrentRoomViewModel;
import com.example.astonindoor.Models.RoomModel;

import java.util.List;

import retrofit2.Call;

public class CurrentPBox extends Fragment {
    private List<RoomModel> roomList;
    Call<List<RoomModel>> serverReqsCall;
    AutoCompleteTextView currentPos;
    private Call<String> postToServer;
    CurrentRoomViewModel currentRoomViewModel;

    public CurrentPBox() {
        super(R.layout.current_pos_box);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.current_pos_box, container, false);
        currentPos = v.findViewById(R.id.currentPostion);
//        currentRoomViewModel = new ViewModelProvider(this,
//                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(DestinationRoomViewModel.class);
        currentRoomViewModel= new ViewModelProvider(this).get(CurrentRoomViewModel.class);


        currentRoomViewModel.getAllRoomNumbers().observe(this.getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
//                ArrayList<String> roomNames = new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_dropdown_item_1line, strings);
                currentPos.setThreshold(1);//will start working from first character
                currentPos.setAdapter(adapter);//setting the adapter data into the
                currentPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String currentPosition = parent.getItemAtPosition(position).toString();
                        currentRoomViewModel.sendToServer(currentPosition);
                        // create Toast with user selected value
                        //Toast.makeText(this.getActivity(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                        System.out.println("CurrentPositon " + currentPosition);
                    }
                });
            }
        });


        return v;
    }



}


/*---------------------------------------------------------------old code-------------------------------------------------------------------------------------*/

//        roomViewModel.getAllUsers().observe(this.getActivity(), new Observer<List<RoomModel>>() {
//            @Override
//            public void onChanged(List<RoomModel> roomModels) {
//                ArrayList<String> roomNames = new ArrayList<>();
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                        (getActivity(), android.R.layout.simple_dropdown_item_1line, roomNames);
//                currentPos.setThreshold(1);//will start working from first character
//                currentPos.setAdapter(adapter);//setting the adapter data into the
//                currentPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String currentPosition = parent.getItemAtPosition(position).toString();
//                        sendToServer(currentPosition);
//                        // create Toast with user selected value
//                        //Toast.makeText(this.getActivity(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
//                        System.out.println("CurrentPositon " + currentPosition);
//                    }
//                });
//            }
//        });


//        serverReqsCall = RetrofitServiceBuilder.getInstance().getRoomListService().getRoomList();
//        serverReqsCall.enqueue(new Callback<List<RoomModel>>() {
//            @Override
//            public void onResponse(@NotNull Call<List<RoomModel>> call, @NotNull Response<List<RoomModel>> response) {
//                System.out.println("response code: " + response.code());
//                ArrayList<String> roomNames = new ArrayList<>();
//                if (response.code() == 200) {
//
//                    roomList = response.body();
//                    for (RoomModel s : roomList) {
//                        roomNames.add(s.getNumRoom());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                            (getActivity(), android.R.layout.simple_dropdown_item_1line, roomNames);
//                    currentPos.setThreshold(1);//will start working from first character
//                    currentPos.setAdapter(adapter);//setting the adapter data into the
//                    currentPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            String currentPosition = parent.getItemAtPosition(position).toString();
//                            sendToServer(currentPosition);
//                            // create Toast with user selected value
//                            //Toast.makeText(this.getActivity(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
//                            System.out.println("CurrentPositon " + currentPosition);
//                        }
//                    });
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RoomModel>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });