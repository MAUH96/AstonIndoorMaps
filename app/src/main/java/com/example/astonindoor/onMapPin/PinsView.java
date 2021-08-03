package com.example.astonindoor.onMapPin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.List;

public class PinsView extends SubsamplingScaleImageView  {
    private final Paint paint = new Paint();
    private final PointF vPin = new PointF();
    private OnPinClickListener onPinClickListener;
    private PointF sPin;
    private Bitmap pin;
    private List<Pin> pinList;

    public PinsView(Context context) {
        this(context, null);
    }

    public PinsView(Context context, AttributeSet attr) {
        super(context, attr);
        pinList = new ArrayList<>();
        initTouchListener();
    }

    public void addCategories(List<Pin> pinList) {
        this.pinList = pinList;
        invalidate();
    }

    public void removeCategories(List<Pin> pinList) {
        this.pinList.removeAll(pinList);
        invalidate();
    }

    public void removeAllCategories() {
        this.pinList.clear();
        invalidate();
    }

    public void setOnPinClickListener(OnPinClickListener listener) {
        onPinClickListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isReady()) {
            return;
        }
        paint.setAntiAlias(true);
        for (Pin categoryPoint: pinList) {
            Bitmap pinIcon = categoryPoint.getImage();
            if (categoryPoint.getPointF() != null && categoryPoint.getImage() != null) {
                PointF point = sourceToViewCoord(categoryPoint.getPointF());
                float vX = point.x - (pinIcon.getWidth()/2);
                float vY = point.y - pinIcon.getHeight();
                canvas.drawBitmap(pinIcon, vX, vY, paint);
            }
        }
    }

    private void initTouchListener() {
        GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isReady() && pinList != null) {
                    PointF tappedCoordinate = new PointF(e.getX(), e.getY());
                    Bitmap clickArea = pinList.get(0).getImage();
                    int clickAreaWidth = clickArea.getWidth();
                    int clickAreaHeight = clickArea.getHeight();
                    for (Pin pin : pinList) {
                        PointF pointCoordinate = sourceToViewCoord(pin.getPointF());
                        int pinX = (int) (pointCoordinate.x);
                        int pinY = (int) (pointCoordinate.y - clickAreaHeight / 2);
                        if (tappedCoordinate.x >= pinX - clickAreaWidth / 2
                                && tappedCoordinate.x <= pinX + clickAreaWidth / 2
                                && tappedCoordinate.y >= pinY - clickAreaHeight / 2
                                && tappedCoordinate.y <= pinY + clickAreaHeight / 2) {
                                onPinClickListener.onPinClick(pin);
                            break;
                        }
                    }
                }
                return true;
            }
        });
        setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}


