package com.example.astonindoor;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;

import com.example.astonindoor.Database.Models.PathModel;
import com.example.astonindoor.Database.ViewModel.NavigationViewModel;

import com.example.astonindoor.onMapPin.Pin;
import com.example.astonindoor.onMapPin.PinsView;
import com.google.android.material.textview.MaterialTextView;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NavMapFragment extends Fragment {


    private Canvas canvas;
    String floorMap;
    private ArrayList<String> nextFloorX = new ArrayList<>();
    private ArrayList<String> nextFloorY = new ArrayList<>();
    private final ArrayList<String> nextFloorRooms = new ArrayList<>();
    private MaterialTextView directionsText;
    private Bitmap displayedMap;

    /**
     * uses Canvas, Paint and Path libraries to draw the path with X and Y coordinates on map image.
     * Uses PinView class to place touchable flags on the path to display directions, change floor or end the navigation
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nextFloorX = new ArrayList<>();
        nextFloorY = new ArrayList<>();

//        String mapFloor2 = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c";
//        String mapFloor3 = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/3%2FMB%203rd%20Floor%20Rev%20N1024_1.jpg?alt=media&token=ffbb6408-d920-496b-b1c5-efaeed9d30c8";

        View v = inflater.inflate(R.layout.
                navigation_map1, container, false);
        PinsView mapImage = v.findViewById(R.id.navImage);
        directionsText = v.findViewById(R.id.directionsText);



        NavigationViewModel roomViewModel = new ViewModelProvider(getActivity()).get(NavigationViewModel.class);

        roomViewModel.getOptimalPath().observe(this.getActivity(), new Observer<List<PathModel>>() {
            @Override
            public void onChanged(List<PathModel> pathModels) {

                int xPathSize = pathModels.get(0).getxDBCoordinates().size() - 1;//paths size
                int startXPoint = Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(0));
                int startYPoint = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(0));
                int endXPoint = Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(xPathSize));
                int endYPoint = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(xPathSize));

                int mapID ;

                if(pathModels.get(0).getRoomNum().get(0).charAt(0)=='2'){
                    displayedMap = BitmapFactory.decodeResource(getResources(), R.drawable.mb_second_floor);
//                    floorMap = mapFloor2;
                    mapID = R.drawable.mb_second_floor;
                } else {
//                    floorMap=mapFloor;
                    displayedMap = BitmapFactory.decodeResource(getResources(), R.drawable.mb_third_floor);
                    mapID = R.drawable.mb_third_floor;

                }

                //creating flags/pin for starting and ending of the path
                Bitmap logoPin = BitmapFactory.decodeResource(getResources(), R.drawable.darkhandm);
                Pin startingPin = new Pin("starting", logoPin, new PointF(startXPoint, startYPoint));
                Pin endingPin = new Pin("ending", logoPin, new PointF(endXPoint, endYPoint));
                List<Pin> pinList = new ArrayList<>();
                pinList.add(startingPin);
                pinList.add(endingPin);


                Glide.with(container)
                        .asBitmap()
                        .load(mapID)
                        .into(new CustomTarget<Bitmap>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                //setting up the map and path line properties
                                resource = Bitmap.createBitmap(resource);
                                canvas = new Canvas(resource);
                                Paint pathPaint = new Paint();
                                pathPaint.setColor(Color.DKGRAY);
                                pathPaint.setStrokeWidth(20);
                                pathPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                                pathPaint.setStrokeJoin(Paint.Join.ROUND);
                                pathPaint.setStrokeCap(Paint.Cap.ROUND);


                                Paint circleUserPaint = new Paint();
                                circleUserPaint.setColor(Color.YELLOW);






                                //looping through the coordinates, checking if the rooms are on the correct floor and designing the path line
                                List<Path> paths = new ArrayList<>();
                                for (int i = 0; i <= xPathSize; i++) {
                                    if (pathModels.get(0).getRoomNum().get(0).charAt(0) == pathModels.get(0).getRoomNum().get(i).charAt(0)) {
                                        if (i == xPathSize) {
                                            break;
                                        }
                                        int startX = Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(i));
                                        int startY = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(i));
                                        int endX = Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(i + 1));
                                        int endY = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(i + 1));
                                        Path path = new Path();
                                        path.moveTo(startX, startY);
                                        path.lineTo(endX, endY);
                                        path.close();
                                        paths.add(path);
                                    } else if (pathModels.get(0).getRoomNum().get(0).charAt(0) != pathModels.get(0).getRoomNum().get(i).charAt(0)) {
                                        nextFloorX.add(pathModels.get(0).getxDBCoordinates().get(i));
                                        nextFloorY.add(pathModels.get(0).getyDBCoordinates().get(i));
                                        nextFloorRooms.add(pathModels.get(0).getRoomNum().get(i));
                                    }
                                }
                                Path pathx = new Path();
                                for (Path path:paths) {
                                    path.close();
                                    pathx.addPath(path);

                                }
                                canvas.drawPath(pathx, pathPaint);
                                directionsText.setTextSize(20);
                                directionsText.setTextAppearance(getContext(),R.style.Widget_MaterialComponents_ActionBar_Solid);
                                directionsText.setText("You can use the touch points hands on the map to get updated directions");

                                //defining touch response of the flag/pin
                                mapImage.setImage(ImageSource.bitmap(resource));
                                mapImage.addCategories(pinList);
                                mapImage.setOnPinClickListener(pin -> {

                                    if (pin.getPintType().equals("starting")) {
                                        directionsText.setTextSize(18);
                                        directionsText.setTextAppearance(getContext(),R.style.Widget_MaterialComponents_ActionBar_Solid);
                                        directionsText.setText("Follow the path your next room should be " + pathModels.get(0).getRoomNum().get(1));


                                        canvas.drawCircle(startXPoint, startYPoint,25,circleUserPaint);

                                        System.out.println(pin.getPintType() + "WORKSSSSSSSSSSSSSSSSSSSSSS");
                                    } else if (pin.getPintType().equals("ending") && nextFloorX.isEmpty()) {


                                        canvas.drawCircle(endXPoint, endYPoint,25,circleUserPaint);
                                        directionsText.setTextSize(20);
                                        directionsText.setTextAppearance(getContext(),R.style.Widget_MaterialComponents_ActionBar_Solid);
                                        directionsText.setText("you have reached your destination");
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                Intent intent = new Intent(getActivity(), MapsActivity.class);
                                                startActivity(intent);
                                            }
                                        }, 10000);   //10 seconds


                                    } else if (pin.getPintType().equals("ending") && !nextFloorX.isEmpty()) {
                                        Intent intent = new Intent(getActivity(), NextFloorNavActivity.class);
                                        intent.putStringArrayListExtra("x", nextFloorX);
                                        intent.putStringArrayListExtra("y", nextFloorY);
                                        intent.putStringArrayListExtra("rooms", nextFloorRooms);
                                        startActivity(intent);
                                    }
                                });


                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });


            }

        });

        return v;

    }

}
