package com.example.astonindoor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.example.astonindoor.DestinationSearchList.DestinationListActivity;

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

    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView searchBarList;
    private ImageView mapImage;
    private  String MAP_LINK;
    private Canvas canvas;
    AutoCompleteTextView currentPos;
    Bitmap displayedMap;

    public HomePageFrag(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.map_activity, container, false);



       // MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c"    ;
//       MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/3%2FMB%203rd%20Floor%20Rev%20N1024_1.jpg?alt=media&token=ffbb6408-d920-496b-b1c5-efaeed9d30c8"    ;
        displayedMap = BitmapFactory.decodeResource(getResources(), R.drawable.mb_second_floor).copy(Bitmap.Config.ARGB_8888, true);
        //gpsButton = (ImageButton) findViewById(R.id.TrackMe);

        startNavigation = (ImageButton) view.findViewById(R.id.CurrentPostionButton);
        mapImage = (ImageView) view.findViewById(R.id.tryImage);
        currentPos = (AutoCompleteTextView) view.findViewById(R.id.currentPostion);
        currentPos.setCompletionHint("Enter your nearest room");

        currentRoomViewModel = new ViewModelProvider(this).get(CurrentRoomViewModel.class);
        currentPos.setVisibility(View.INVISIBLE);

        int id = R.drawable.mb_second_floor;

  //      goButton.setVisibility(View.INVISIBLE);
        startNavigation.setOnClickListener(currentPosTextBox);



        /**
         * design path
         */
        Glide.with(this)
                .asBitmap()
                .load(id)
                .into(new CustomTarget<Bitmap>() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createBitmap(resource);
                        canvas = new Canvas(resource);
                        mapImage.setImageBitmap(resource);


                        mapImage.setOnTouchListener((view, motionEvent) -> {

                            final float x = motionEvent.getX();
                            final float y = motionEvent.getY();
                            float lastXAxis = x;
                            float lastYAxis = y;
                            System.out.println("X coord ="+ lastXAxis +" " + "Y coord= " + lastYAxis);

                            return true;
                        });

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

                        Intent intent = new Intent(getContext(), DestinationListActivity.class);
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

    private final View.OnClickListener searchActivity = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), DestinationListActivity.class);
            startActivity(intent);

        }
    };


    private final View.OnClickListener currentPosTextBox = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            currentPos.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);



        }
    };

}
