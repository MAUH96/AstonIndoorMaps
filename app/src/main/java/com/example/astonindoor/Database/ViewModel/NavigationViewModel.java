package com.example.astonindoor.Database.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.astonindoor.Database.Models.PathModel;
import com.example.astonindoor.Database.Respositories.DestinationRoomsRepository;
import com.example.astonindoor.Database.Respositories.NavigationRepository;

import java.util.List;

public class NavigationViewModel extends ViewModel {
    private final NavigationRepository navigationRepository;

    public NavigationViewModel() {
        super();
        navigationRepository = new NavigationRepository();
    }
    public MutableLiveData<List<PathModel>> getOptimalPath() {
        return navigationRepository.getOptimalPath();
    }

}
