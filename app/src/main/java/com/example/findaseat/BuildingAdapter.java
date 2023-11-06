package com.example.findaseat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {

    private List<Building> buildings;
    private LayoutInflater inflater;
    // Optionally, if you want to handle clicks:
    // private View.OnClickListener clickListener;

    // Constructor
    public BuildingAdapter(Context context, List<Building> buildings /*, View.OnClickListener clickListener*/) {
        this.inflater = LayoutInflater.from(context);
        this.buildings = buildings;
        // this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BuildingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_building, parent, false);
        // Optionally, if you want to handle clicks:
        // view.setOnClickListener(clickListener);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingViewHolder holder, int position) {
        Building building = buildings.get(position);
        holder.buildingName.setText(building.getBuildingName());
    }

    @Override
    public int getItemCount() {
        return buildings != null ? buildings.size() : 0;
    }

    public class BuildingViewHolder extends RecyclerView.ViewHolder {
        TextView buildingName;

        public BuildingViewHolder(View itemView) {
            super(itemView);
            buildingName = itemView.findViewById(R.id.building_name);
            // Initialize your other views here if you have more
        }
    }
}

