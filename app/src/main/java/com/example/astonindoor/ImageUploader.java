package com.example.astonindoor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageUploader extends AsyncTask<String, Void, Bitmap>{

    private ImageView mapImage;
    private String imageLink;
    private Bitmap bitmap;
    public ImageUploader(ImageView mapImage, String imageLink){
        this.mapImage=mapImage;
        this.imageLink=imageLink;

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            InputStream inputStream = new java.net.URL(imageLink).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}