    package com.example.astonindoor.SearchBar;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.inputmethod.EditorInfo;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.SearchView;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProviders;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.astonindoor.Database.AppViewModel;
    import com.example.astonindoor.MapsActivity;
    import com.example.astonindoor.Models.RoomImages;
    import com.example.astonindoor.R;
    import com.miguelcatalan.materialsearchview.MaterialSearchView;

    import java.util.ArrayList;
    import java.util.List;

    public class SearchMenuActivity extends AppCompatActivity implements SearchListAdapter.onRoomListener  {

        private RecyclerView searchBarList;
        private  SearchListAdapter  sAdapter;
        private AppViewModel allRooms;
        private List<RoomImages> mRoomImages = new ArrayList<>();

        private static final String TAG = "SearchMenuActivity";




        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_activity);
            searchBarList = (RecyclerView)findViewById(R.id.searchBar_list);
            allRooms = ViewModelProviders.of(this).get(AppViewModel.class);

            retrieveRoomDb();

            setListInRecyclerView();





        }
        public void retrieveRoomDb(){
            allRooms.getLiveRoomImages().observe(this, new Observer<List<RoomImages>>() {
                @Override
                public void onChanged(List<RoomImages> roomImages) {
                    if(!mRoomImages.isEmpty()){
                        mRoomImages.clear();
                    }else {
                        mRoomImages.addAll(roomImages);
                        sAdapter.notifyDataSetChanged();
                    }
                }
            });

        }




        private void setListInRecyclerView(){

            //searchBarList.setLayoutManager(new LinearLayoutManager(this));
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
            searchBarList.setLayoutManager(layoutManager);
            searchBarList.setHasFixedSize(true);// dont want to change the size of recylceview
            sAdapter = new SearchListAdapter(mRoomImages,this); // this refer to onRoomListener interface
            searchBarList.setAdapter(sAdapter);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.nav_search_bar,menu);

            // Get the SearchView and set the searchable configuration
            MenuItem searchItem = menu.findItem(R.id.list_searching);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    sAdapter.getFilter().filter(newText);

                    return false;
                }
            });

            return true;
        }

        @Override
        public void onRoomClick(int position) {
            Log.d(TAG, "roomClicked");
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

            // Follow the video tutorial to see how
            // to pass the coordinates or the image chosen to maps

        }
    }
