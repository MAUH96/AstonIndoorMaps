package com.example.astonindoor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.astonindoor.Camera.CameraIntentActivity;
import com.example.astonindoor.Database.AppViewModel;
import com.example.astonindoor.ImageProcessing.BitmapProcessing;
import com.example.astonindoor.Models.RoomImages;
import com.example.astonindoor.SearchBar.SearchMenuActivity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    //buttons
    private ImageButton gpsButton;
    private ImageButton goButton;
    private ImageButton camButton;

    //class objects
    private BitmapProcessing imageProcess;
    //private CameraIntent camIntent;


    //Android and others
    private RelativeLayout pLayer;
    private androidx.appcompat.widget.SearchView searchView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private  AssetManager assetManager;
    private StorageReference mStorageRef;

    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView searchBarList;
    private ImageView mapImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FloorMapView mapView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);


        gpsButton = (ImageButton) findViewById(R.id.TrackMe);
        goButton = (ImageButton) findViewById(R.id.GoButton);
        camButton = (ImageButton) findViewById(R.id.CameraButton);



    /**
    * NEEDED WHEN DATABASE ADDED
    */
//        allRooms = ViewModelProviders.of(this).get(AppViewModel.class);
//        dbImages = new roomList();
//        System.out.println("HEREEEEEEE"+  dbImages.getAllImg(this, allRooms).size());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private View.OnClickListener searchActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MapsActivity.this, SearchMenuActivity.class);
               startActivity(intent);
        }
    };


    private View.OnClickListener image = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(MapsActivity.this, CameraIntentActivity.class);
           //startActivity(intent);
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//
//
//            viewModel.getLiveRoomImages().observe(this, new Observer<List<RoomImages>>() {
//                @Override
//                public void onChanged(List<RoomImages> roomImages) {
//                    try {
//                        //you will loop to and try all the links: --> it would be faster if you ask user on which floor he is
//                        // (only if is too slow with searching)
//                        getStorageImage(roomImages.get(0));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//
//        }
//    }


    @Override
    protected void onStart(){
        super.onStart();
        gpsButton.setOnClickListener(image);
        goButton.setOnClickListener(searchActivity);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
  /**  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker aston Uni

        LatLng astonFloor0 = new LatLng(52.486860, -1.890165);


        mMap.addMarker(new MarkerOptions().position(astonFloor0).title("Entrance "));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(astonFloor0, 20));
        mMap.getFocusedBuilding();
        //mainBuilding.getLevels().get(mainBuilding.getActiveLevelIndex());

    }**/

//    public static Bitmap getBitmapFromAsset(Context context, String filePath) throws IOException {
//        AssetManager assetManager = context.getAssets();
//
//        InputStream istr;
//        Bitmap bitmap ;
//
//        istr = assetManager.open(filePath);
//        bitmap = BitmapFactory.decodeStream(istr);
//
//        return bitmap;
//    }

    public void showCamera(View view) {
    }
//    public void getStorageImage( RoomImages imageUrl) throws IOException {
//
//        StorageReference imagePath = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl.getImage());
//
//        final File localFile = File.createTempFile("images", "jpg");
//        imagePath.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        // Successfully downloaded data to local file
//                        // ..
//
//
//                        //Get user take picture
//                        Bitmap imageUser = BitmapFactory.decodeFile(camIntent.getCurrentPhotoPath());
//
//                        Bitmap smallerUserPic = imageProcess.resizeImg(imageUser);
//                        Bitmap grayUserPic = imageProcess.changeToGrayScale(smallerUserPic);
//
//                        //get DBfile
//                        Bitmap imageDB = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                        Bitmap smallerImgDB = imageProcess.resizeImg(imageDB); //imageProcess class method to reduce the img
//                        Bitmap grayImgDB = imageProcess.changeToGrayScale(smallerImgDB); //imageProcess (class) to turn img in grayscale
//                        // simpleImageViewLion.setImageBitmap(greyBit); //set the image in the front-end view of the app
//                        //Glide.with(context).load(grayBit).into(simpleImageViewLion);
//
//                        //hash both of em
//                        int avrageRgbImgDb = imageProcess.avarageColor(grayImgDB);
//                        int avarageRgbImgUser = imageProcess.avarageColor(grayUserPic);
//                        String hashedDbImg = imageProcess.pHashing(grayImgDB,avrageRgbImgDb);
//                        String hashedUserImg = imageProcess.pHashing(grayUserPic,avarageRgbImgUser);
//
//                        //compare them
//                        if(imageProcess.compareImgHash(hashedUserImg,hashedDbImg)){
//
//                            finishActivity(REQUEST_IMAGE_CAPTURE);
//
//                            Toast.makeText(getApplicationContext(),"SAMEEE IMAGEEE!!!",Toast.LENGTH_LONG).show();
//                            Log.d("TAGggggggggggggg", "SAMEEE IMAGEEE");
//
//                        }else{
//                            Toast.makeText(getApplicationContext(),"NOT SAME IMAGEEEEEEE",Toast.LENGTH_LONG).show();
//                            Log.d("TAGggggggg", "NOT SAME IMAGEEE");
//
//                        }
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                // ...
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        //map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
//        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
}
