package com.example.astonindoor.Database.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonindoor.Database.Models.PathModel;
import com.example.astonindoor.Database.Respositories.DestinationRoomsRepository;

import java.util.List;

public class NavigationViewModel extends ViewModel {
    private NavigationViewModel roomRepository;

    public NavigationViewModel() {
        super();
        roomRepository = new NavigationViewModel();
    }
    public MutableLiveData<List<PathModel>> getOptimallPath() {
        return roomRepository.getOptimallPath();
    }

}
