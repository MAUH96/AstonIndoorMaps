//package com.example.astonindoor;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Camera;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.ColorMatrix;
//import android.graphics.ColorMatrixColorFilter;
//import android.graphics.Paint;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.view.View;
//
//
//import androidx.core.content.FileProvider;
//import androidx.fragment.app.FragmentActivity;
//
//import com.google.firebase.storage.StorageReference;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.nio.ByteBuffer;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//
//
//public class CameraIntent {
//
//    Activity activity;
//    File image;
//    private String currentPhotoPath; // this is the image take by the user
//    private static final int REQUEST_IMAGE_CAPTURE = 1;
//
//    public CameraIntent(Activity activity) {
//        this.activity = activity;
//    }
//
//
//    /**
//     * Define the button to show the camera
//     * Creates image file jps by using createImageFile() method;
//     */
//
//    public View.OnClickListener showCamera = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//
//
//
//            // Ensure that there's a camera activity to handle the intent
//            if (pictureIntent.resolveActivity(activity.getPackageManager()) != null) {
//                // Create the File where the photo should go
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//
//                }
//                // Continue only if the File was successfully created
//                if (photoFile != null) {
//                    Uri photoURI =
//                            FileProvider.getUriForFile(
//                                    activity,
//                                    "com.example.astonindoor.fileprovider",
//                                    photoFile);
//                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                    activity.startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
//
//                }
//            }
//            ;
//        }
//
//    };
//
//    /**
//     * Contructs the image file name in Jpg
//     *
//     * @return
//     * @throws IOException
//     */
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = activity.getExternalFilesDir((Environment.DIRECTORY_PICTURES));
//        image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        this.currentPhotoPath = image.getAbsolutePath();
//
//        return image;
//    }
//
//    /**
//     * temporary saves image in gallary
//     * (maybe not needed )
//     */
//
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        activity.sendBroadcast(mediaScanIntent);
//
//
//    }
//
//    public String getCurrentPhotoPath() {
//        return currentPhotoPath;
//    }
//
//    public Bitmap getUserPicInBitMap() {
//
//
//        return BitmapFactory.decodeFile(currentPhotoPath);
//    }
//
//
//
//}
//
//
//
//
//
//
