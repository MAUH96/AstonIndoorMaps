package com.example.astonindoor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.astonindoor.Database.ViewModel.CurrentRoomViewModel;


import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity {

    private CurrentRoomViewModel currentRoomViewModel;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
       currentRoomViewModel = new ViewModelProvider(this).get(CurrentRoomViewModel.class);
        final ArrayList<String> navValid = new ArrayList<>();

        currentRoomViewModel.isValid().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                System.out.println("Test "+ strings.get(0));
                if(strings.get(0).equals("true")) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.main_activity, NavMapFragment.class, null)
                            .commit();
                } else{
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .add(R.id.main_activity, HomePageFrag.class, null)
                            .commit();
                }
            }
        });






    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}