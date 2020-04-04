package com.example.astonindoor;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.astonindoor.Database.AppViewModel;
import com.example.astonindoor.ImageProcessing.BitmapProcessing;
import com.example.astonindoor.Models.RoomImages;
import com.example.astonindoor.OldRoomDatabase.FloorMapView;
import com.example.astonindoor.SearchBar.SearchListAdapter;
import com.example.astonindoor.SearchBar.SearchMenuActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //buttons
    private ImageButton gpsButton;
    private ImageButton goButton;
    private ImageButton camButton;

    //class objects
    private BitmapProcessing imageProcess;
    private CameraIntent camIntent;


    //Android and others
    private GoogleMap mMap;
    private RelativeLayout pLayer;
    private androidx.appcompat.widget.SearchView searchView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private  AssetManager assetManager;

    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView searchBarList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FloorMapView mapView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // setListInRecyclerView();


        pLayer = (RelativeLayout) findViewById(R.id.relLayer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //buttons initialising
        gpsButton = (ImageButton) findViewById(R.id.TrackMe);
        goButton = (ImageButton) findViewById(R.id.GoButton);
        camButton = (ImageButton) findViewById(R.id.CameraButton);


        mapFragment.getMapAsync(this);
        imageProcess = new BitmapProcessing();


        //goButton.setOnClickListener(listActive);
        assetManager = getAssets();
        String test ="";

        try {
            InputStream is = assetManager.open("image1.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Bitmap bit = imageProcess.resizeImg(bitmap);
            Bitmap greyBit =imageProcess.changeToGrayScale(bit);
            int avarageColor = imageProcess.avarageColor(greyBit);
            String firstImghash = imageProcess.pHashing(greyBit, avarageColor);
            String secondImghash = imageProcess.pHashing(greyBit, avarageColor);
            test = imageProcess.compareImgHash(firstImghash,secondImghash);
            //String roomByImg = roomNo.getRoomByImg(firstImghash);
            Log.d("TAG",test );
        } catch (IOException e) {
            e.printStackTrace();
        }



//        String ss = "";
//        try {
////            ss = new String(imageProcess.convertBitmapToByteArrayUncompressed(getBitmapFromAsset(this, "image.jpg")));
//            Log.d("TAG", String.valueOf(imageProcess.convertBitmapToByteArrayUncompressed(getBitmapFromAsset(this, "image.jpg"))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    /**
    * NEEDED WHEN DATABASE ADDED
    */
//        allRooms = ViewModelProviders.of(this).get(AppViewModel.class);
//        dbImages = new roomList();
//        System.out.println("HEREEEEEEE"+  dbImages.getAllImg(this, allRooms).size());

    }


    private View.OnClickListener searchActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MapsActivity.this, SearchMenuActivity.class);
               startActivity(intent);
        }
    };


//    private View.OnClickListener listActive = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(MapsActivity.this, RetrieveDb.class);
//               startActivityForResult(intent,1 );
//        }
//    };

    private View.OnClickListener image = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MapsActivity.this, Image.class);
            startActivity(intent);
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ImageProcessing.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            File imgFile = new File(imageProcess.getCurrentPhotoPath());
//
//
//        }
//    }


    @Override

    protected void onStart(){
        super.onStart();
        camIntent = new CameraIntent(this);
        camButton.setOnClickListener(camIntent.showCamera);// camera button event
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker aston Uni

        LatLng astonFloor0 = new LatLng(52.486860, -1.890165);

        mMap.addMarker(new MarkerOptions().position(astonFloor0).title("Entrance "));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(astonFloor0, 20));
        mMap.getFocusedBuilding();
        //mainBuilding.getLevels().get(mainBuilding.getActiveLevelIndex());

    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) throws IOException {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap ;

            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);


        return bitmap;
    }


    public void showCamera(View view) {
    }
}
