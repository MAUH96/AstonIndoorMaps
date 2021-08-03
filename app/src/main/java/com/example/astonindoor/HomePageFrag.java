package com.example.astonindoor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.astonindoor.Database.ViewModel.CurrentRoomViewModel;
import com.example.astonindoor.DestinationSearchList.SearchMenuActivity;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class HomePageFrag extends Fragment {

    //buttons
    private ImageButton gpsButton;
    private ImageButton goButton;
    private ImageButton startNavigation;
    private CurrentRoomViewModel currentRoomViewModel;

    //class objects
    //private CameraIntent camIntent;


    //Android and others
    private RelativeLayout pLayer;
    private androidx.appcompat.widget.SearchView searchView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private AssetManager assetManager;
    private StorageReference mStorageRef;

    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView searchBarList;
    private ImageView mapImage;
    private  String MAP_LINK;
    private Canvas canvas;;
    AutoCompleteTextView currentPos;

    public HomePageFrag(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map_activity, container, false);



        MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c"    ;
       // MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c"    ;
        //gpsButton = (ImageButton) findViewById(R.id.TrackMe);
        goButton = (ImageButton) view.findViewById(R.id.GoButton);
        startNavigation = (ImageButton) view.findViewById(R.id.CameraButton);
        mapImage = (ImageView) view.findViewById(R.id.tryImage);
        currentPos = (AutoCompleteTextView) view.findViewById(R.id.currentPostion);

        currentRoomViewModel = new ViewModelProvider(this).get(CurrentRoomViewModel.class);
        currentPos.setVisibility(View.INVISIBLE);



        goButton.setVisibility(View.INVISIBLE);
        startNavigation.setOnClickListener(currentPosTextBox);


        /**
         * design path
         */
        Glide.with(this)
                .asBitmap()
                .load(MAP_LINK)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createBitmap(resource);
                        canvas = new Canvas(resource);;
                        mapImage.setImageBitmap(resource);



                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });




        currentRoomViewModel= new ViewModelProvider(this).get(CurrentRoomViewModel.class);


        currentRoomViewModel.getAllRoomNumbers().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
//                ArrayList<String> roomNames = new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (getContext(), android.R.layout.simple_dropdown_item_1line, strings);
                currentPos.setThreshold(1);//will start working from first character
                currentPos.setAdapter(adapter);//setting the adapter data into the
                currentPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String currentPosition = parent.getItemAtPosition(position).toString();
                        currentRoomViewModel.sendToServer(currentPosition);

                        Intent intent = new Intent(getContext(), SearchMenuActivity.class);
                        startActivity(intent);

                        System.out.println("CurrentPositon " + currentPosition );
                        //isSelected=true;

                    }
                });

            }
        });

        return view;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private View.OnClickListener searchActivity = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), SearchMenuActivity.class);
            startActivity(intent);

        }
    };


    private View.OnClickListener currentPosTextBox = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            currentPos.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
//            goButton.setVisibility(View.VISIBLE);
//            goButton.setOnClickListener(searchActivity);



            // boolean running = true;
//            boolean isSelected = Boolean.parseBoolean(currentRoomViewModel.isValid());
//            System.out.println(isSelected);
//            while(running) {
//                if (isSelected) {
//                    transaction.setReorderingAllowed(true)
//                            .remove(currentPBox)
//                            .commit();
//
//                    running = false;
//                }
//            }


        }
    };

}
