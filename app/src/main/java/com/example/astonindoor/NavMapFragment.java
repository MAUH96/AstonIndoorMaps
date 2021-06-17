package com.example.astonindoor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class NavMapFragment extends Fragment {

    ImageView mapImage;
    private String MAP_LINK;
    private Canvas canvas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MAP_LINK = "https://firebasestorage.googleapis.com/v0/b/astonindoor.appspot.com/o/1%2FMB%202nd%20Floor%20Rev%20Q-1.jpg?alt=media&token=1f5cfdb0-bccb-40e2-9a27-9c0418e8992c";
        View v =  inflater.inflate(R.layout.
                navigation_map1, container, false);
        mapImage = (ImageView) v.findViewById(R.id.navImage);


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
        return v;

    }
}
