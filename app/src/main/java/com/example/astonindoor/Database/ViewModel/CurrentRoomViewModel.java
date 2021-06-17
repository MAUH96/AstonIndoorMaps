package com.example.astonindoor.Database.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonindoor.Database.Respositories.CurrentRoomRepository;
import com.example.astonindoor.Database.Respositories.DestinationRoomsRepository;

import java.util.List;

public class CurrentRoomViewModel extends ViewModel {


    private CurrentRoomRepository roomRepository;


    public CurrentRoomViewModel() {
        super();
        roomRepository = new CurrentRoomRepository();
    }

    public MutableLiveData<List<String>> getAllRoomNumbers() {
        return roomRepository.getLiveRoomNum();
    }

    public void sendToServer(String selectedRoom) {
        roomRepository.sendToServer(selectedRoom);
    }
    public String isValid(){
        return roomRepository.isSelected();
    }
}
