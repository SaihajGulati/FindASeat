package com.example.findaseat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private List<String> timeSlotList; // Assume each time slot is represented as a String for simplicity


    public TimeSlotAdapter(List<String> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    @Override
    public TimeSlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time_slot, parent, false); // Use the new layout file here
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeSlotViewHolder holder, int position) {
        String timeSlot = timeSlotList.get(position);
        if(timeSlot != null) {
            holder.timeSlotTextView.setText(timeSlot);
        }
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        public TextView timeSlotTextView; // This TextView will show the time slot

        public TimeSlotViewHolder(View itemView) {
            super(itemView);
            timeSlotTextView = itemView.findViewById(R.id.timeSlotTextView); // Link to the TextView in item_time_slot.xml
        }
    }
}
