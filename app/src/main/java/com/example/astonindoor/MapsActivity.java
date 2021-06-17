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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.astonindoor.Camera.CameraIntentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
//import com.example.astonindoor.Database.AppViewModel;

import com.example.astonindoor.Database.ViewModel.CurrentRoomViewModel;
import com.example.astonindoor.Models.RoomModel;
import com.example.astonindoor.SearchBar.SearchMenuActivity;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class MapsActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
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
    private  AssetManager assetManager;
    private StorageReference mStorageRef;

    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView searchBarList;
    private ImageView mapImage;
    private  String MAP_LINK;
    private Canvas canvas;
    private List<RoomModel>roomList;
    Call<List<RoomModel>>serverReqsCall;
    private String isValid;
    AutoCompleteTextView currentPos;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FloorMapView mapView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c"    ;


        currentRoomViewModel = new ViewModelProvider(this).get(CurrentRoomViewModel.class);

        MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c"    ;
        //gpsButton = (ImageButton) findViewById(R.id.TrackMe);
        goButton = (ImageButton) findViewById(R.id.GoButton);
        startNavigation = (ImageButton) findViewById(R.id.CameraButton);
        mapImage = (ImageView) findViewById(R.id.tryImage);
        currentPos = (AutoCompleteTextView) findViewById(R.id.currentPostion);



        currentRoomViewModel= new ViewModelProvider(this).get(CurrentRoomViewModel.class);


        currentRoomViewModel.getAllRoomNumbers().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
//                ArrayList<String> roomNames = new ArrayList<>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (MapsActivity.super.getApplicationContext(), android.R.layout.simple_dropdown_item_1line, strings);
                currentPos.setThreshold(1);//will start working from first character
                currentPos.setAdapter(adapter);//setting the adapter data into the
                currentPos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String currentPosition = parent.getItemAtPosition(position).toString();
                        currentRoomViewModel.sendToServer(currentPosition);

                        Intent intent = new Intent(MapsActivity.this, SearchMenuActivity.class);
                        startActivity(intent);

                        System.out.println("CurrentPositon " + currentPosition );
                        //isSelected=true;




                    }
                });

            }
        });

        currentPos.setVisibility(View.INVISIBLE);


        /**
         * design path
         */
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(MAP_LINK)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createBitmap(resource);
                        canvas = new Canvas(resource);

                        Paint drawPaint = new Paint();

                        drawPaint.setColor(Color.RED);
                        //drawPaint.setAntiAlias(true);
                        drawPaint.setStrokeWidth(20);
                        drawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                        drawPaint.setStrokeJoin(Paint.Join.ROUND);
                        drawPaint.setStrokeCap(Paint.Cap.ROUND);

                        Path path = new Path();
                        path.moveTo(128, 648);
                        path.lineTo(119, 883);
                        path.close();
                        //canvas.scale(mapImage.getWidth(),mapImage.getHeight());
                        canvas.drawPath(path, drawPaint);


                        //mapImage.setScaleY(canvas.getHeight());
                        //mapImage.setScaleX(canvas.getWidth());
                        mapImage.setImageBitmap(resource);


                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        mapImage.setOnTouchListener((view, motionEvent) -> {

            final float x = motionEvent.getX();
            final float y = motionEvent.getY();
            float lastXAxis = x;
            float lastYAxis = y;
            System.out.println("X coord ="+ Float.toString(lastXAxis) +" " + "Y coord= " +Float.toString(lastYAxis));

            return true;
        });
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }



    private View.OnClickListener searchActivity = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MapsActivity.this, SearchMenuActivity.class);
                startActivity(intent);

        }
    };


    private View.OnClickListener currentPosTextBox = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            currentPos.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
            goButton.setVisibility(View.VISIBLE);
            goButton.setOnClickListener(searchActivity);



            // boolean running = true;
            boolean isSelected = Boolean.parseBoolean(currentRoomViewModel.isValid());
            System.out.println(isSelected);
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



    @Override
    protected void onStart(){
        super.onStart();
        goButton.setVisibility(View.INVISIBLE);
        startNavigation.setOnClickListener(currentPosTextBox);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }



}
