package com.example.astonindoor.SearchBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astonindoor.Models.FloorModel;
import com.example.astonindoor.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> implements Filterable {
    private List<FloorModel> listOfRooms = new ArrayList<>(); //need to be changed
    private List<FloorModel> copyListOfRooms; //this copy will have all the items
    // the first list will be used to filter and remove and fullList will be used to add back
    //the removed items.x

    private onRoomListener onRoomListener;

   // private static int lastPositionChecked = -1; // -1 no position selected by default
    //0 there is a first default selection



    SearchListAdapter(onRoomListener onRoomListener){

        this.onRoomListener=onRoomListener;

    }

    public void setListOfRooms(List<FloorModel> listOfRooms){
        this.listOfRooms=listOfRooms;
        copyListOfRooms = new ArrayList<>(listOfRooms); // new copye of arraylist above

    }
    static class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView roomNum;
        TextView floorName;
        onRoomListener onRoomListener;
        SearchViewHolder(@NonNull View itemView){
            super(itemView);
        };

        SearchViewHolder(@NonNull View itemView, onRoomListener onRoomListener) {
            super(itemView);
            roomNum = itemView.findViewById(R.id.roomNumber);
            floorName = itemView.findViewById(R.id.floorName);

            this.onRoomListener = onRoomListener;
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            try {
                onRoomListener.onRoomClick(getAdapterPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }//inner class finished

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchbar_list, parent,false);
        return new SearchViewHolder(v , onRoomListener); // internal class

    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        FloorModel currentItem = listOfRooms.get(position);
        holder.floorName.setText(currentItem.getNumRoom());
        holder.roomNum.setText(currentItem.getRoomName());
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
            List<FloorModel> filteredList = new ArrayList<>(); // contains filtered items


            /*
             * contraint represent the logic of the search bar input field
             */
            if(constraint == null || constraint.length()==0){ // if input field is empty
                //show the full list
                filteredList.addAll(copyListOfRooms);

            }else{
                String searchedText = constraint.toString().toLowerCase().trim();


                for(FloorModel item : copyListOfRooms){
                    if(item.getNumRoom().toLowerCase().contains(searchedText) || item.getRoomName().toLowerCase().contains(searchedText) ){
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



    /**
     * interface detects the click
     * onRoomClick() is used to send the position of the clicked item
     */
    public interface onRoomListener{
        void onRoomClick(int position) throws JSONException;

    }

}
