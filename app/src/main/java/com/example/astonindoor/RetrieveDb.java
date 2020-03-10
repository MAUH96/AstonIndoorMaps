package com.example.astonindoor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.astonindoor.Database.AppViewModel;
import com.example.astonindoor.Models.RoomImages;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDb extends AppCompatActivity{
    ListView listView;
    List<String> list;
    List<String>img = new ArrayList<>();
    ArrayAdapter<String> images;
    private RoomImages roomimg;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_db);
        list = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
        roomimg = new RoomImages();
        images = new ArrayAdapter<String>(this, R.layout.user_info,R.id.roomImg,list);


        AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);


        viewModel.getLiveRoomImages().observe(this, new Observer<List<RoomImages>>() {
            @Override
            public void onChanged(List<RoomImages> roomImages){
                String imgUrls = "";

                for(int i = 0; i<roomImages.size(); i++){

                  img.add(roomImages.get(i).getImage() );
                    System.out.println("IMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM" + img);
                }
            }
        });
    }
}
