package com.example.astonindoor;

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


import java.util.ArrayList;
import java.util.List;

public class NavMapFragment extends Fragment {

    private String MAP_LINK;
    private Canvas canvas;
    private ArrayList<String> nextFloorX = new ArrayList<>();
    private ArrayList<String> nextFloorY = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c";

        View v =  inflater.inflate(R.layout.
                navigation_map1, container, false);
        PinsView mapImage = v.findViewById(R.id.navImage);
        if(getActivity()==null){
            System.out.println("false");
        }
        NavigationViewModel roomViewModel = new ViewModelProvider(getActivity()).get(NavigationViewModel.class);

        roomViewModel.getOptimallPath().observe(this.getActivity(), new Observer<List<PathModel>>() {
            @Override
            public void onChanged(List<PathModel> pathModels) {

                int xPathSize = pathModels.get(0).getxDBCoordinates().size()-1;
                int startXPoint = Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(0));
                int startYPoint = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(0));
                int endXPoint = Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(xPathSize));
                int endYPoint = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(xPathSize));




                Bitmap logoPin = BitmapFactory.decodeResource(getResources(), R.drawable.darkhandm);
                Pin startingPin = new Pin("starting",logoPin,new PointF(startXPoint, startYPoint));
                Pin endingPin = new Pin("ending",logoPin,new PointF(endXPoint, endYPoint));

                List<Pin> pinList = new ArrayList<>();
                pinList.add(startingPin);
                pinList.add(endingPin);


                /**
                 * design path
                 */
                Glide.with(container)
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

                                List<Path> paths = new ArrayList<>();

                                for(int i = 0; i<xPathSize; i++){
                                    if(pathModels.get(0).getRoomNum().get(0).charAt(0) == pathModels.get(0).getRoomNum().get(i).charAt(0)){
                                        if(i == xPathSize){
                                            break;
                                        }

                                        int startX =  Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(i));
                                        int startY = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(i));
                                        int endX =  Integer.parseInt(pathModels.get(0).getxDBCoordinates().get(i+1));
                                        int endY = Integer.parseInt(pathModels.get(0).getyDBCoordinates().get(i+1));
                                        Path path = new Path();
                                        path.moveTo(startX,startY);
                                        path.lineTo(endX,endY);
                                        paths.add(i, path);

                                    } else if (pathModels.get(0).getRoomNum().get(0).charAt(0) != pathModels.get(0).getRoomNum().get(i).charAt(0)){
                                        nextFloorX.add(pathModels.get(0).getxDBCoordinates().get(i));
                                        nextFloorY.add(pathModels.get(0).getyDBCoordinates().get(i));
                                    }




                                }

                                Path pathx = new Path();
                                for (Path path: paths) {

                                    pathx.addPath(path);

                                }
                                pathx.close();


                                canvas.drawPath(pathx, drawPaint);
                                //mapImage.setScaleY(canvas.getHeight());
                                //mapImage.setScaleX(canvas.getWidth());
                                mapImage.setImage(ImageSource.bitmap(resource));
                                mapImage.addCategories(pinList);
                                mapImage.setOnPinClickListener(pin -> {

                                    if(pin.getPintType().equals("starting")) {

                                        System.out.println(pin.getPintType() + "WORKSSSSSSSSSSSSSSSSSSSSSS");

                                    } else if (pin.getPintType().equals("ending") && nextFloorX.isEmpty()){
                                        System.out.println("end path end alkdandalndalk");

                                    } else if (pin.getPintType().equals("ending") && !nextFloorX.isEmpty()){
                                        Intent intent = new Intent(getActivity(), NextFloorNavActivity.class);
                                        intent.putStringArrayListExtra("x", nextFloorX);
                                        intent.putStringArrayListExtra("y", nextFloorY);
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
