package com.example.findaseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BookingActivity extends AppCompatActivity {

    private TextView buildingNameTextView;
    private TextView buildingDescriptionTextView;
    private Building building;

    private RecyclerView timeSlotsRecyclerView;
    private TimeSlotAdapter timeSlotAdapter;
    private List<TimeSlot> timeSlotList; // You need to define this list


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        buildingNameTextView = findViewById(R.id.buildingNameTextView);
        buildingDescriptionTextView = findViewById(R.id.buildingDescriptionTextView);
        TextView numberOfSeatsView = findViewById(R.id.numberOfSeats);

        // Initialize your RecyclerView here
        timeSlotsRecyclerView = findViewById(R.id.recycler_view_time_slots); // Replace with your actual RecyclerView ID

        // Initialize your timeSlotList with TimeSlot objects
        timeSlotList = new ArrayList<>();

        timeSlotList = createTimeSlotDummyData();

// Convert your List<TimeSlot> to List<String>
        List<String> timeSlotStrings = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotList) {
            timeSlotStrings.add(timeSlot.toString()); // replace getTimeSlotString() with your actual method
        }

// Now you can set up the RecyclerView with the adapter
        timeSlotAdapter = new TimeSlotAdapter(timeSlotStrings);
        timeSlotsRecyclerView.setAdapter(timeSlotAdapter);
        timeSlotsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("BUILDING")) {
            building = (Building) intent.getSerializableExtra("BUILDING"); // Ensure that Building class implements Serializable

            if(building != null) {
                // Now that we have confirmed building is not null, it's safe to call methods on it
                int numberOfSeats = building.getNumberOfSeats(); // This should be your own method to get the actual number
                String seatsText = "Number of Seats Available: " + numberOfSeats;
                numberOfSeatsView.setText(seatsText);

                // Set the building name and description on the TextViews
                buildingNameTextView.setText(building.getBuildingName());
                buildingDescriptionTextView.setText(building.getDescription());
            } else {
                // Handle the case where the building object is still null even if the intent has "BUILDING" extra
                Toast.makeText(this, "Building data is unavailable.", Toast.LENGTH_LONG).show();
            }
        } else {
            // Handle the case where there is no building data passed
            Toast.makeText(this, "No building information provided!", Toast.LENGTH_LONG).show();
        }

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity
                finish();
            }
        });
    }

    private String formatTime(int hour, int minute) {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }

    private List<TimeSlot> createTimeSlotDummyData() {
        List<TimeSlot> dummyList = new ArrayList<>();

        // Use the formatTime method to create time strings
        dummyList.add(new TimeSlot(formatTime(9, 0), true));
        dummyList.add(new TimeSlot(formatTime(10, 0), true));

        // ... more dummy data

        return dummyList;
    }

}
