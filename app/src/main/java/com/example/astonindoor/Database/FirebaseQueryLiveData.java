package com.example.astonindoor.Database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import android.os.Handler;
import android.util.Log;

import com.example.astonindoor.Models.RoomImages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private final Query query;
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private boolean listenerRemovePending = false;
    private final ValueEventListener valueListener = new mValueEventListener();
    private final Handler handler = new Handler();

    private final Runnable removeListner = new Runnable() {
        @Override
        public void run() {
            query.removeEventListener(valueListener);
            listenerRemovePending = false;
        }
    };


    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference databaseRef) {
        this.query = databaseRef;
    }

    @Override
    protected void onActive() {
       if(listenerRemovePending){
           handler.removeCallbacks(removeListner);
       }else  {
           query.addValueEventListener(valueListener);
       }
       listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        handler.postDelayed(removeListner,2000);
        listenerRemovePending=true;
    }

    private class mValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(LOG_TAG,  "Cannot listen to query " + query, databaseError.toException());
        }
    }



}
