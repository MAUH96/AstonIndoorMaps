package com.example.astonindoor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.astonindoor.Camera.CameraIntentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
//import com.example.astonindoor.Database.AppViewModel;

import com.example.astonindoor.Database.RetrofitServiceBuilder;
import com.example.astonindoor.Models.FloorModel;
import com.example.astonindoor.Models.RoomImages;
import com.example.astonindoor.SearchBar.SearchMenuActivity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    //buttons
    private ImageButton gpsButton;
    private ImageButton goButton;
    private ImageButton camButton;

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
    private AutoCompleteTextView currentPos;
    private  String MAP_LINK;
    private Canvas canvas;
    private List<FloorModel>roomList;
    Call<List<FloorModel>>serverReqsCall;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FloorMapView mapView;
        CurrentPBox currentPBox = new CurrentPBox();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_cont, CurrentPBox.class, null)
                    .commit();
        }

        MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c"    ;
        gpsButton = (ImageButton) findViewById(R.id.TrackMe);
        goButton = (ImageButton) findViewById(R.id.GoButton);
        camButton = (ImageButton) findViewById(R.id.CameraButton);
        mapImage = (ImageView) findViewById(R.id.tryImage);


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


    private View.OnClickListener image = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(MapsActivity.this, CameraIntentActivity.class);
           //startActivity(intent);
        }
    };



    @Override
    protected void onStart(){
        super.onStart();
        gpsButton.setOnClickListener(image);
        goButton.setOnClickListener(searchActivity);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
    public AutoCompleteTextView populateTextBox(Context context){


        serverReqsCall = RetrofitServiceBuilder.getInstance().getRoomListService().getRoomList();
        serverReqsCall.enqueue(new Callback<List<FloorModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<FloorModel>> call, @NotNull Response<List<FloorModel>> response) {
                System.out.println("response code: " + response.code());
                ArrayList<String> roomNames = new ArrayList<>();
                if (response.code() == 200) {

                    roomList = response.body();
                    for (FloorModel s : roomList) {
                        roomNames.add(s.getNumRoom());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (context, android.R.layout.select_dialog_item, roomNames);

                    currentPos = (AutoCompleteTextView) findViewById(R.id.currentPostion);
                    currentPos.setThreshold(1);//will start working from first character
                    currentPos.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


                }
            }


            @Override
            public void onFailure(Call<List<FloorModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return currentPos;
    }
}




