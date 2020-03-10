package com.example.astonindoor.ImageProcessing;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class BitmapProcessing {


    /**
     * scales images to 32 x 32
     *
     * @param DBImage
     * @return
     */
    public Bitmap resizeImg(Bitmap DBImage) {

        Bitmap scaledimg = Bitmap.createScaledBitmap(DBImage, 8, 8, false);
        return scaledimg;

    }


    public Bitmap changeToGrayScale(Bitmap imgToChange) {


        int height = imgToChange.getHeight();
        int width = imgToChange.getWidth();

        Bitmap grayScaleimg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(grayScaleimg);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(imgToChange, 0, 0, paint);


        return grayScaleimg;

    }


    /**
     * METHOD TO BE REVIEWED, IF TO LEAVE THEM OR NOT
     *
     * @param bit
     * @return
     */


    public int avarageColor(Bitmap bit) {
        Bitmap bitmap = bit;
        int redColors = 0;
        int greenColors = 0;
        int blueColors = 0;
        int pixelCount = 0;

        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                int c = bitmap.getPixel(x, y);
                pixelCount++;
                redColors += Color.red(c);
                greenColors += Color.green(c);
                blueColors += Color.blue(c);
            }
        }
//   calculate average of bitmap r,g,b values
        int red = (redColors / pixelCount);
        int green = (greenColors / pixelCount);
        int blue = (blueColors / pixelCount);

        int avarage = Color.rgb(red, green, blue);

        return avarage;
    }
    public String pHashing(Bitmap bmp, int avarage) {

        String hash = "";
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                hash += (bmp.getPixel(x, y)> avarage?"1":"0");
                // for(int s = 0; s<=i.length(); s++){

            }
        }
        return hash;
    }


    public String hashBitmap(Bitmap bmp) {

        String hash = "";
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                String i = String.valueOf(bmp.getPixel(x, y));
                // for(int s = 0; s<=i.length(); s++){
                hash = i;
            }
        }
            return hash;

    }

    public String compareImgHash(String i, String x) {
        String same = "";
        int hd = 0;
        for (int y = 0; y < i.length() && y < x.length(); y++) {
            if (i.charAt(y) != x.charAt(y)) {
                hd++;
            }
        }
        if(hd==0 || hd<=2){
            same = "same images";

        }else {
            same = "not same";
        }
        return same;
    }
}

//        long hash = 64; //or a higher prime at your choice
//        for(int x = 0; x < bmp.getWidth(); x++){
//            for (int y = 0; y < bmp.getHeight(); y++){
//                hash *= (bmp.getPixel(x,y) + 64);
//            }
//        }