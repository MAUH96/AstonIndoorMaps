package com.example.astonindoor.onMapPin;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class Pin {
    private String pintType;
    private Bitmap image;
    private PointF pointF;

    public Pin(String category, Bitmap image, PointF pointF) {
        this.pintType = category;
        this.image = image;
        this.pointF = pointF;
    }

    public String getPintType() {
        return pintType;
    }

    public void setPintType(String pintType) {
        this.pintType = pintType;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public PointF getPointF() {
        return pointF;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }
}

