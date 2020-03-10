package com.example.astonindoor.SearchBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astonindoor.Models.RoomImages;
import com.example.astonindoor.Models.RoomModel;
import com.example.astonindoor.R;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> implements Filterable {
    List<RoomImages> listOfRooms = new ArrayList<>(); //need to be changed
    List<RoomImages> fullListOfRooms; //this copy will have all the items
    // the first list will be used to filter and remove and fullList will be used to add back
    //the removed items.

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView roomNum;
        TextView floorName;


        public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        roomNum = itemView.findViewById(R.id.roomNumber);
        floorName= itemView.findViewById(R.id.floorName);
        }
    }
    public SearchListAdapter(){}

    public void setSearchList(List<RoomImages> listOfRooms){
        this.listOfRooms=listOfRooms;
        fullListOfRooms= new ArrayList<>(listOfRooms); // new copye of arraylist above
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchbar_list, parent,false);
        SearchViewHolder svh = new SearchViewHolder(v); // internal class
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        RoomImages currentItem = listOfRooms.get(position);
        holder.floorName.setText(currentItem.getImage());
        holder.roomNum.setText(currentItem.getImage());
    }

    @Override
    public int getItemCount() {
        return listOfRooms.size();
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RoomImages> filteredList = new ArrayList<>(); // contains filtered items

            /**
             * contraint represent the logic of the search bar input field
             */
            if(constraint == null || constraint.length()==0){ // if input field is empty
                //show the full list
                filteredList.addAll(fullListOfRooms);
            }else{
                String searchedText = constraint.toString().toLowerCase().trim();

                for(RoomImages item : fullListOfRooms){
                    if(item.getImage().toLowerCase().contains(searchedText)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listOfRooms.clear();
            listOfRooms.addAll((List)results.values);
            notifyDataSetChanged();
        }

    };

}
