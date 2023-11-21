package com.example.findaseat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Math;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    private List<TimeSlot> outdoorSlots;
    private List<TimeSlot> indoorSlots;

    private List<TimeSlot> openSlots;

    private OnReserveButtonClickListener reserveButtonClickListener;

    /*public interface OnTimeSlotClickListener {
        void onTimeSlotClick(TimeSlot timeSlot);
    }*/

    public interface OnReserveButtonClickListener {
        void onReserveButtonClick(TimeSlot timeSlot);
    }

    public TimeSlotAdapter(OnReserveButtonClickListener listener) {
        this.openSlots = new ArrayList<>();
        this.indoorSlots = new ArrayList<>();
        this.outdoorSlots = new ArrayList<>();
        this.reserveButtonClickListener = listener;
    }

    @Override
    public TimeSlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_slot, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeSlotViewHolder holder, int position) {
        TimeSlot timeSlot = openSlots.get(position);
        holder.bind(timeSlot, reserveButtonClickListener);
    }

    @Override
    public int getItemCount() {
        return openSlots.size();
    }

    public void setTimeSlots(List<Integer> indoorSlots, List<Integer> outdoorSlots, double open) {

        //must convert to timeslots and then put in here
        List<TimeSlot> slots = makeTimeSlots(indoorSlots, outdoorSlots, open);
        for (int i = 0; i < outdoorSlots.size(); i++)
        {
            if (slots.get(i).getIndoorSeats() > 0 || slots.get(i).getOutdoorSeats() > 0)
            {
                openSlots.add(slots.get(i));
            }
        }

        notifyDataSetChanged();
    }

    private List<TimeSlot> makeTimeSlots(List<Integer> indoorSlots, List<Integer> outdoorSlots, double open) {

        List<TimeSlot> times = new ArrayList<>();
        for (int i = 0; i < indoorSlots.size(); i++)
        {
            double startTime = i*0.5 + open;
            double endTime = startTime + 0.5;
            String start = convTimeToString(startTime);
            String end = convTimeToString(endTime);

            TimeSlot ts = new TimeSlot(i, start, end, outdoorSlots.get(i), indoorSlots.get(i));
            times.add(ts);
        }

        return times;
    }

    private String convTimeToString(double time)
    {
        double hour = Math.floor(time);
        //am by default
        String end = " AM";

        double minutes = time - hour;


        if (hour >= 12)
        {
            end = " PM";
        }

        //need to check this separately from PM bc only subtract starting at 13, but add PM from 12
        if (hour >= 13){
            hour -= 12;
        }

        //bc is a decimal like 0.5 so need to multiply by 60
        minutes *= 60;

        //format to get rid of decimals
        DecimalFormat hourFormatter = new DecimalFormat("0.##");
        DecimalFormat minuteFormatter = new DecimalFormat("00.##");

        //convert to string
        String result = hourFormatter.format(hour).toString() + ":" + minuteFormatter.format(minutes).toString();

        result += end;

        return result;
    }


    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartTime, tvEndTime, tvAvailableSeats;

        public Button btnReserve;

        public TimeSlotViewHolder(View itemView) {
            super(itemView);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            tvAvailableSeats = itemView.findViewById(R.id.tvAvailableSeats);
            btnReserve = itemView.findViewById(R.id.btnReserve);
        }

        public void bind(final TimeSlot timeSlot, final OnReserveButtonClickListener reserveListener) {
            tvStartTime.setText(timeSlot.getStartTime());
            tvEndTime.setText(timeSlot.getEndTime());
            tvAvailableSeats.setText(String.valueOf(timeSlot.getIndoorSeats() + timeSlot.getOutdoorSeats()));

            btnReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reserveListener.onReserveButtonClick(timeSlot);
                }
            });
        }
    }
}