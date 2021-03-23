//package com.example.astonindoor.Camera;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Camera;
//import android.graphics.ImageFormat;
//import android.graphics.Point;
//import android.graphics.SurfaceTexture;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.params.StreamConfigurationMap;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Size;
//import android.view.TextureView;
//import android.widget.Button;
//import android.widget.TabHost;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.example.astonindoor.CameraIntent;
//import com.example.astonindoor.R;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class CameraIntentActivity extends AppCompatActivity {
//
//    private static final String TAG = "PreviewSize";
//    private TextureView mTextureView;
//    private Button mCamButton;
//    private String cameraId;
//
//    TextureView.SurfaceTextureListener mTextureListener;
//
//    /**
//     *  this method is used to setup the camera preview insight the Texture view;
//     *  openCamera(width,height) method inflates TextureView with camera preview by matching
//     *  its height and width
//     */
//    private TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
//        @Override
//        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//            setCameraSizes(width, height);
//            openCamera();
//        }
//
//        @Override
//        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//        }
//
//        @Override
//        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//            return false;
//        }
//
//        @Override
//        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//        }
//    };
//    private Size mPreviewSize;
//    private CameraDevice camDevice;
//
//
//    private static class CompareSizeByArea implements Comparator<Size> {
//        /**
//         *  signum return -1 if the values s negative, 0 in is zero and 1 if is postive.
//         *  Area = left hand side width * left hand side height (lhs)
//         *  compare two display sizes by their area
//         * @param lhs
//         * @param rhs
//         * @return
//         */
//        @Override
//        public int compare(Size lhs, Size rhs) {
//            return Long.signum((long) (lhs.getWidth() * lhs.getHeight()) -
//                    (long) (rhs.getWidth() * rhs.getHeight()));
//        }
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera);
//
//        mTextureView = findViewById(R.id.pic_texture);
//        mCamButton = findViewById(R.id.btn_takepicture);
//    }
//
//
//
//    CameraDevice.StateCallback camDeviceStateCallback = new CameraDevice.StateCallback() {
//        @Override
//        public void onOpened(@NonNull CameraDevice camera) {
//            camDevice = camera;
//            Toast.makeText(getApplicationContext(), "camera open", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onDisconnected(@NonNull CameraDevice camera) {
//            camera.close();
//            camDevice = null;
//        }
//
//        @Override
//        public void onError(@NonNull CameraDevice camera, int error) {
//            camera.close();
//            camDevice = null;
//            Activity activity = CameraIntentActivity.this;
//            if (activity != null) {
//                activity.finish();
//            }
//        }
//    };
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        /**
//         * the camera size is being set up here( not onCreate() method) and it resources will be stopped if user opens another app but
//         * it will resume/ or comeback once the user is back at our app
//         */
//        if (mTextureView.isAvailable()) {
//            setCameraSizes(mTextureView.getWidth(), mTextureView.getHeight());
//
//        } else {
//            mTextureView.setSurfaceTextureListener(textureListener);
//        }
//    }
//
//
//    private void setCameraSizes(int width, int height) {
//        Activity activity = CameraIntentActivity.this;
//        CameraManager camResources = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
//
//        try {
//            assert camResources != null;
//            for (String cameraId : camResources.getCameraIdList()) { // cameraID from camResources represent each camera in our device
//                CameraCharacteristics cameraCharacteristics = camResources.getCameraCharacteristics(cameraId);
//                Integer camFacing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
//                if (camFacing != null && camFacing == CameraCharacteristics.LENS_FACING_FRONT) {
//                    continue;
//                }
//                //string config map that has the sizes of the camera preview
//                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//                assert map != null;
//
//                // pics the larges size from the list;
//                Size largestRatioImg = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
//                        new CompareSizeByArea());
//
//                Point displaySize = new Point();
//                activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
//                int maxPreviewWidth = displaySize.x;
//                int maxPreviewHeight = displaySize.y;
//                mPreviewSize = choosePreviewSize(map.getOutputSizes(SurfaceTexture.class), width, height,
//                        maxPreviewWidth,maxPreviewHeight, largestRatioImg);
//                cameraId = cameraId;
//                return; //return out off this method
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * bigEnough will contain the resolution(display sensor) size big enough for our display(sufraceview)
//     * height/width calculates the aspect ratio  to see if ti matches our TextureView
//     * @param choices
//     * @param
//     * @param
//     * @return
//     */
//
//    private static Size choosePreviewSize(Size[] choices, int textureWidth, int textureHeight,
//             int maxPreviewWidth, int maxPreviewHeight, Size aspectRatio) {
//        List<Size> bigEnough = new ArrayList<>();
//
//        int w = aspectRatio.getWidth();
//        int h = aspectRatio.getHeight();
//        for (Size option : choices) {
//            // check is aspect ration match the texture view and the height and width of preview sensor(option)
//            //are big enough for our textureView
//            if (option.getHeight() == option.getWidth() * h / w && option.getWidth() <= maxPreviewWidth
//                    && option.getHeight() <= maxPreviewHeight) {
//                if(option.getWidth()>=textureWidth && option.getHeight()>= textureHeight){
//                    bigEnough.add(option);
//                }
//            }
//        }
//        //if there is a bigEnough size
//        if (bigEnough.size() > 0) {
//            //finds minimum value from the list by comparing it the area of two options
//            return Collections.min(bigEnough, new CompareSizeByArea());
//        } else {
//            Log.e(TAG, "Couldn't find any suitable preview size");
//            return choices[0];
//        }
//    }
//
//    private void openCamera() {
//        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            cameraManager.openCamera(cameraId, camDeviceStateCallback, null);
//
//        } catch (CameraAccessException e){
//
//
//
//}
//
//        }
//                }