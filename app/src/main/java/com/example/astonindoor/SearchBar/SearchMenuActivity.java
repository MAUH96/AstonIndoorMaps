package com.example.astonindoor.SearchBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


//import com.example.astonindoor.Database.RetrofitService.RoomsListService;
import com.example.astonindoor.Database.ViewModel.CurrentRoomViewModel;
import com.example.astonindoor.Database.ViewModel.DestinationRoomViewModel;
import com.example.astonindoor.MapsActivity;
import com.example.astonindoor.Models.RoomModel;
import com.example.astonindoor.NavMapFragment;
import com.example.astonindoor.R;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class SearchMenuActivity extends AppCompatActivity implements SearchListAdapter.onRoomListener {

    private RecyclerView searchBarList;
    private SearchListAdapter sAdapter;
    //private AppViewModel allRooms;
    private List<RoomModel> roomList;
    private Call<String> postToServer;
    private DestinationRoomViewModel roomViewModel;

    Call<List<RoomModel>> serverReqsCall;

    private static final String TAG = "SearchMenuActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        roomViewModel = new ViewModelProvider(this).get(DestinationRoomViewModel.class);

        setListInRecyclerView();

    }

    /**
     * Set destination room list by retireving all the rooms from the database
     */
    private void setListInRecyclerView() {

        searchBarList = (RecyclerView) findViewById(R.id.searchBar_list);

        //searchBarList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchBarList.setHasFixedSize(true);// dont want to change the size of recylceview
        searchBarList.setLayoutManager(layoutManager);
        sAdapter = new SearchListAdapter(this); // this refer to onRoomListener interface


        roomViewModel.getAllRoomNodes().observe(this, new Observer<List<RoomModel>>() {
            @Override
            public void onChanged(List<RoomModel> roomModels) {
                sAdapter.setListOfRooms(roomModels);
                searchBarList.setAdapter(sAdapter);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Get the SearchView and set the searchable configuration
        inflater.inflate(R.menu.nav_search_bar, menu);
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
    public void onRoomClick(final int position) throws JSONException {

        roomViewModel.sendToServer(position);

//        NavMapFragment fragment = new NavMapFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.maps_activity, fragment);
//        transaction.commit();

    }

}
