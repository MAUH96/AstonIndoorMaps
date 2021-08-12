package com.example.astonindoor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.astonindoor.onMapPin.OnPinClickListener;
import com.example.astonindoor.onMapPin.Pin;
import com.example.astonindoor.onMapPin.PinsView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class NextFloorNavActivity extends AppCompatActivity {
    String MAP_LINK;
    private ArrayList<String> xCoodinatesPath = new ArrayList<>();
    private ArrayList<String>yCoordinatesPath = new ArrayList<>();
    private ArrayList<String>rooms = new ArrayList<>();
    private Canvas canvas;
    private MaterialTextView directionsText;
//    private Bitmap displayedMap;



    /**
     * drawing the next floor map navigation
     * @param savedInstanceState
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_floor_activity);
        ///MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/3%2FMB%203rd%20Floor%20Rev%20N1024_1.jpg?alt=media&token=ffbb6408-d920-496b-b1c5-efaeed9d30c8";
        PinsView mapImage = findViewById(R.id.mapImage);

        xCoodinatesPath = getIntent().getStringArrayListExtra("x");
        yCoordinatesPath = getIntent().getStringArrayListExtra("y");
        rooms= getIntent().getStringArrayListExtra("rooms");
        directionsText = (MaterialTextView) findViewById(R.id.directionsNextFloor);


        int pathSize = xCoodinatesPath.size()-1;

        int startXPoint = Integer.parseInt(xCoodinatesPath.get(0));
        int startYPoint = Integer.parseInt(yCoordinatesPath.get(0));

        int endXPoint = Integer.parseInt(xCoodinatesPath.get(pathSize));
        int endYPoint = Integer.parseInt(yCoordinatesPath.get(pathSize));

        int displayedMap;

        if(rooms.get(0).charAt(0)=='2'){
//            displayedMap = BitmapFactory.decodeResource(getResources(), R.drawable.mb_second_floor);
            displayedMap = R.drawable.mb_second_floor;
//                    floorMap = mapFloor2;
        } else {
//                    floorMap=mapFloor3;
//            displayedMap = BitmapFactory.decodeResource(getResources(), R.drawable.mb_third_floor);
            displayedMap = R.drawable.mb_third_floor;
        }


        Bitmap logoPin = BitmapFactory.decodeResource(getResources(), R.drawable.darkhandm);

        Pin startingPin = new Pin("starting", logoPin, new PointF(startXPoint, startYPoint));
        Pin endingPin = new Pin("ending", logoPin, new PointF(endXPoint, endYPoint));

        List<Pin> pinList = new ArrayList<>();
        pinList.add(startingPin);
        pinList.add(endingPin);



        Glide.with(this)
                .asBitmap()
                .load(displayedMap)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        resource = Bitmap.createBitmap(resource);

                        canvas = new Canvas(resource);
                        Paint drawPaint = new Paint();
                        drawPaint.setColor(Color.BLUE);
                        drawPaint.setStrokeWidth(20);
                        drawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                        drawPaint.setStrokeJoin(Paint.Join.ROUND);
                        drawPaint.setStrokeCap(Paint.Cap.ROUND);

                        Paint circleUserPaint = new Paint();
                        circleUserPaint.setColor(Color.YELLOW);


                        List<Path> paths = new ArrayList<>();
                        for (int i = 0; i < pathSize; i++) {
                            if (i == pathSize) {
                                break;
                            }
                            int startX = Integer.parseInt(xCoodinatesPath.get(i));
                            int startY = Integer.parseInt(yCoordinatesPath.get(i));
                            int endX = Integer.parseInt(xCoodinatesPath.get(i + 1));
                            int endY = Integer.parseInt(yCoordinatesPath.get(i + 1));
                            Path path = new Path();
                            path.moveTo(startX, startY);
                            path.lineTo(endX, endY);
                            paths.add(i, path);
                        }

                        Path pathx = new Path();
                        for (Path path : paths) {
                            pathx.addPath(path);
                        }
                        pathx.close();
                        canvas.drawPath(pathx, drawPaint);


                        mapImage.setImage(ImageSource.bitmap(resource));
                        mapImage.addCategories(pinList);
                        mapImage.setOnPinClickListener(new OnPinClickListener() {
                            @Override
                            public void onPinClick(Pin pin) {

                                if (pin.getPintType().equals("starting")) {

                                    canvas.drawCircle(startXPoint, startYPoint,25,circleUserPaint);
                                    System.out.println(xCoodinatesPath.get(0));
                                    directionsText.setTextSize(18);
                                    directionsText.setTextAppearance(getApplicationContext(),R.style.Widget_MaterialComponents_ActionBar_Solid);
                                    directionsText.setText("Follow the path your next room should be " + rooms.get(0));



                                } else if (pin.getPintType().equals("ending")) {

                                    canvas.drawCircle(endXPoint, endYPoint,25,circleUserPaint);
                                    directionsText.setTextSize(20);
                                    directionsText.setTextAppearance(getApplicationContext(),R.style.Widget_MaterialComponents_ActionBar_Solid);
                                    directionsText.setText("You have reached your destination");
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                            startActivity(intent);
                                        }
                                    }, 10000);   //10 seconds

                                }
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }


}