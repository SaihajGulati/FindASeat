package com.example.findaseat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {

    private List<Building> buildingList;

    public BuildingAdapter(List<Building> buildingList) {
        this.buildingList = buildingList;
    }

    @Override
    public BuildingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_list_item, parent, false);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuildingViewHolder holder, int position) {
        Building building = buildingList.get(position);
        if(building != null) {
            if(holder.buildingNameTextView != null) {
                holder.buildingNameTextView.setText(building.getBuildingName());
            }
            if(holder.descriptionTextView != null) {
                holder.descriptionTextView.setText(building.getDescription());
            }
            if(holder.viewButton != null) {
                holder.viewButton.setText(building.getBuildingName());
                holder.viewButton.setOnClickListener(v -> {

                    Intent intent = new Intent(v.getContext(), BookingActivity.class);

                    intent.putExtra("BUILDING", building);
                    v.getContext().startActivity(intent);
                });
            }
        }
    }
    @Override
    public int getItemCount() {
        return buildingList.size();
    }

    public static class BuildingViewHolder extends RecyclerView.ViewHolder {
        public TextView buildingNameTextView;
        public TextView descriptionTextView;
        public Button viewButton; // This button is used to view the building

        public BuildingViewHolder(View itemView) {
            super(itemView);
            buildingNameTextView = itemView.findViewById(R.id.buildingName);
            descriptionTextView = itemView.findViewById(R.id.description);
            viewButton = itemView.findViewById(R.id.btn_view); // Initialize the view button
        }
    }
}
