//package com.example.astonindoor.Database;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.LiveData;
//import android.os.Handler;
//import android.util.Log;
//
//import com.example.astonindoor.Models.RoomImages;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.List;
//
//
//public class DatabaseLiveRepository extends LiveData<DataSnapshot> {
//    private final Query query;
//    private static final String LOG_TAG = "FirebaseQueryLiveData";
//    private boolean listenerRemovePending = false;
//    private final ValueEventListener valueListener = new mValueEventListener(); // instance of subclass at the end of this page
//    private final Handler handler = new Handler();
//
//    /**
//     * explain what does this do
//     *
//     * handler are bounded with a thread where is instantiated(Runnable) to handle its messages and execute the runnable object.
//     * The threads'messages and runnable objects are added in the message queue created by the thread itself. These object and messages are
//     * excuted as the leave the queue.
//     */
//    private final Runnable removeListner = new Runnable() {
//        @Override
//        public void run() {
//            query.removeEventListener(valueListener);
//            listenerRemovePending = false;
//        }
//    };
//
//
//    public DatabaseLiveRepository(Query query) {
//        this.query = query;
//    }
//
//    /**
//     * Constructor to add database reference
//     */
//    public DatabaseLiveRepository(DatabaseReference databaseRef) {
//        this.query = databaseRef;
//    }
//
//    /**
//     * explain what is this doing
//     */
//
//    @Override
//    protected void onActive() {
//       if(listenerRemovePending){
//           handler.removeCallbacks(removeListner); // removes runnable messages
//       }else  {
//           query.addValueEventListener(valueListener);
//       }
//       listenerRemovePending = false;
//    }
//
//    /**
//     *  if the
//     */
//    @Override
//    protected void onInactive() {
//        handler.postDelayed(removeListner,2000);
//        listenerRemovePending=true;
//    }
//
//    private class mValueEventListener implements ValueEventListener{
//
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            setValue(dataSnapshot);
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//            Log.e(LOG_TAG,  "Cannot listen to query " + query, databaseError.toException());
//        }
//    }
//
//
//
//}
