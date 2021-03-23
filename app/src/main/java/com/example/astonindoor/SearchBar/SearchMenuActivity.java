    package com.example.astonindoor.SearchBar;

    import android.content.Intent;
    import android.os.Build;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.inputmethod.EditorInfo;
    import android.widget.Toast;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.SearchView;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProviders;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.astonindoor.Database.AppViewModel;
    //import com.example.astonindoor.Database.RoomsListService;
    import com.example.astonindoor.Database.RetrofitServiceBuilder;
    import com.example.astonindoor.MapsActivity;
    import com.example.astonindoor.Models.FloorModel;
    import com.example.astonindoor.Models.RoomImages;
    import com.example.astonindoor.R;
    import com.example.astonindoor.SearchBar.SearchListAdapter.SearchViewHolder;
    import com.google.gson.JsonObject;
    import com.google.gson.JsonParser;

    import org.jetbrains.annotations.NotNull;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class SearchMenuActivity extends AppCompatActivity implements SearchListAdapter.onRoomListener  {

        private RecyclerView searchBarList;
        private  SearchListAdapter  sAdapter;
        private AppViewModel allRooms;
        private List<FloorModel>roomList;
        private Call<String> postToServer;

        Call<List<FloorModel>>serverReqsCall;

        private static final String TAG = "SearchMenuActivity";

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_activity);
            setListInRecyclerView();


        }

//        public void retrieveRoomDb(){
//            allRooms.getLiveRoomImages().observe(this, new Observer<List<RoomImages>>() {
//
//                @Override
//                public void onChanged(List<RoomImages> roomImages) {
//                       if(!mRoomImages.isEmpty()){
//                           mRoomImages.addAll(roomImages);
//
//                           sAdapter.notifyDataSetChanged();
//
//                       }
//
//                    }
//
//            });
//
//        }




        private void setListInRecyclerView(){

            //            allRooms = ViewModelProviders.of(this).get(AppViewModel.class);

            searchBarList = (RecyclerView)findViewById(R.id.searchBar_list);

            //searchBarList.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            searchBarList.setHasFixedSize(true);// dont want to change the size of recylceview
            searchBarList.setLayoutManager(layoutManager);
            sAdapter = new SearchListAdapter(this); // this refer to onRoomListener interface



            serverReqsCall = RetrofitServiceBuilder.getInstance().getRoomListService().getRoomList();
            serverReqsCall.enqueue(new Callback<List<FloorModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<FloorModel>> call, @NotNull Response<List<FloorModel>> response) {
                    System.out.println("response code: "+response.code());
                    if(response.code()==200){

                        roomList = response.body();

                        sAdapter.setListOfRooms(roomList);
                        searchBarList.setAdapter(sAdapter);

                        //Toast.makeText(SearchMenuActivity.this, s, Toast.LENGTH_LONG).show();





                    }

//                        List<String> rooms = new ArrayList<>();
//                    for (int i = 0; i < roomList.size(); i++) {
//                        rooms.add(roomList.get(i).getRoom1());
//                    }

                }


                @Override
                public void onFailure(Call<List<FloorModel>> call, Throwable t) {
                    t.printStackTrace();
                }
            });

//            allRooms.getLiveFloorNum().observe(this, new Observer<List<FloorModel>>() {
//                @Override
//                public void onChanged(List<FloorModel> floorModels) {
//
//                    sAdapter.setListOfRooms(floorModels);
//                    searchBarList.setAdapter(sAdapter);
//                }
//            });




        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            // Get the SearchView and set the searchable configuration
            inflater.inflate(R.menu.nav_search_bar,menu);
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

            String currentRoomNum = roomList.get(position).getNumRoom();
            HashMap<String,String> currentPosition = new HashMap<>();
            currentPosition.put("numRoom",""+currentRoomNum.toString());
            System.out.println(currentPosition);
            postToServer = RetrofitServiceBuilder.getInstance().getRoomListService()
                    .sendCurrentPos(currentPosition);
            postToServer.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {

                        System.out.println("sent to the server " + response.body());
                    }else if(response.body()!=null){
                        System.out.println("value null");
                    }else{
                        System.out.println("not successful");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }

    }
