package com.example.findaseat;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class TimeSlotAdapterShow extends RecyclerView.Adapter<TimeSlotAdapterShow.TimeSlotViewHolder> {

    private List<TimeSlot> timeSlots;

    TextView tvStartTime;

    TextView tvEndTime;
    private OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void onChangeClick(TimeSlot timeSlot);
        void onCancelClick(TimeSlot timeSlot);
    }

    public TimeSlotAdapterShow(List<TimeSlot> timeSlots, OnButtonClickListener listener) {
        this.timeSlots = timeSlots;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_time_slot, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        // Bind your TimeSlot data to your views here
        TimeSlot currentSlot = timeSlots.get(position);
        holder.bind(currentSlot, listener);
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        // Define your view items here
        Button changeButton;
        Button cancelButton;
        // Other views like TextViews, ImageViews, etc

        public TimeSlotViewHolder(View itemView) {
            super(itemView);
            // Initialize your view items
            changeButton = itemView.findViewById(R.id.btnChange); // Replace with your button ID
            cancelButton = itemView.findViewById(R.id.btnCancel); // Replace with your button ID
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
        }

        public void bind(final TimeSlot timeSlot, final OnButtonClickListener listener) {
            tvStartTime.setText(timeSlot.getStartTime());
            tvEndTime.setText(timeSlot.getEndTime());
            changeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onChangeClick(timeSlot);
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCancelClick(timeSlot);
                }
            });
        }
    }
}

