package com.example.astonindoor.SearchBar;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;

import com.example.astonindoor.Models.RoomImages;
import com.example.astonindoor.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchableActivity {

    public void setSearchView(final MaterialSearchView searchBar, final ArrayList<RoomImages> roomdData){


        searchBar.setSuggestions(new String[]{Arrays.toString(roomdData.toArray())});
        searchBar.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
}
