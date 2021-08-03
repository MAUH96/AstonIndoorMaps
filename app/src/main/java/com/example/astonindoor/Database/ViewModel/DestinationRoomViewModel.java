package com.example.astonindoor.Database.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonindoor.Database.Respositories.DestinationRoomsRepository;
import com.example.astonindoor.Database.Models.RoomModel;

import java.util.List;

public class DestinationRoomViewModel extends ViewModel {
    private DestinationRoomsRepository roomRepository;

    public DestinationRoomViewModel() {
        super();
        roomRepository = new DestinationRoomsRepository();
    }

    public MutableLiveData<List<RoomModel>> getAllRoomNodes() {
        return roomRepository.getLiveRoomNode();
    }

    public void sendToServer(int selectedRoom) {
        roomRepository.sendToServer(selectedRoom);
    }


}
