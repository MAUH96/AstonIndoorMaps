package com.example.astonindoor;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.astonindoor.ImageProcessing.BitmapProcessing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Is he image class where the image is outputed
 * the class uses BitmapsProcessing class to recolor and resize the image.
 */

public class Image extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2);

        ImageView simpleImageViewLion = (ImageView) findViewById(R.id.simpleImageViewLion);//get the id of first image view
        ImageView simpleImageViewMonkey = (ImageView) findViewById(R.id.simpleImageViewMonkey);//get the id of second image view

        //Class object
        BitmapProcessing imageProcess = new BitmapProcessing();
        AssetManager assetManager = getAssets();

        try {
            InputStream imgIs = assetManager.open("image1.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(imgIs);

            Bitmap bit = imageProcess.resizeImg(bitmap); //imageProcess class method to reduce the img
            Bitmap greyBit = imageProcess.changeToGrayScale(bit); //imageProcess (class) to turn img in grayscale
            simpleImageViewLion.setImageBitmap(greyBit); //set the image in the front-end view of the app.
        } catch (IOException e) {
            e.printStackTrace();
        }



        simpleImageViewLion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Lion", Toast.LENGTH_LONG).show();//display the text on image click event
            }
        });
        simpleImageViewMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Monkey", Toast.LENGTH_LONG).show();//display the text on image click event
            }
        });
    }

}
