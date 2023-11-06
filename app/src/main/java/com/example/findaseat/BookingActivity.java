package com.example.findaseat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity implements TimeSlotAdapter.OnTimeSlotClickListener {

    private ListView lvTimeSlots;
    private TextView tvBuildingDescription;
    private TextView tvSeatAvailability;

    // A list to hold time slots for display
    private ArrayList<TimeSlot> timeSlotsList;
    private ArrayAdapter<TimeSlot> adapter;

    private String building;

    private View tvBuildingId;
    private RecyclerView rvTimeSlots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        // Initialize components and layout views
        initializeUI();

        // Retrieve the building ID passed from MainActivity
        Intent intent = getIntent();

        if (intent != null) {
            String buildingId = intent.getStringExtra("BUILDING_ID");
            if (buildingId != null && !buildingId.isEmpty()) {
                // Use the building ID to load the appropriate time slots and prepare the UI
                loadBuildingDetails(buildingId);
            }
        }
    }


    private void initializeUI() {
        tvBuildingId = findViewById(R.id.tvBuildingId);
        rvTimeSlots = findViewById(R.id.rvTimeSlots);

        // Set up the RecyclerView with a LinearLayoutManager and an adapter (you need to define the adapter)
        rvTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        // Assuming you have a TimeSlotAdapter class that takes in a list of TimeSlot objects
        rvTimeSlots.setAdapter(new TimeSlotAdapter(new ArrayList<>(), this));

        // Initialize other UI components as necessary
    }

    private void loadBuildingDetails(String buildingId) {
        tvBuildingId.setText(String.format("Building ID: %s", buildingId));

        // Fetch the time slots for the building from Firebase
        fetchTimeSlots(buildingId);
    }

    //interface for accessing building stuff from backend
    private void fetchTimeSlots(String buildingId) {
        // Placeholder for Firebase call or another data source retrieval
        // This method would be responsible for fetching time slots and notifying the adapter
        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

        // Suppose you get a list of TimeSlot objects from Firebase
        // After fetching data, update the RecyclerView's adapter
        TimeSlotAdapter adapter = (TimeSlotAdapter) rvTimeSlots.getAdapter();
        if (adapter != null) {
            adapter.setTimeSlots(timeSlots);  // You'll need to create this method in your adapter
            adapter.notifyDataSetChanged();
        }
    }



    private void reserveTimeSlot(TimeSlot slot) {
        // Check if the user is logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // Not logged in
            Toast.makeText(this, "You must be logged in to make a reservation.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assuming TimeSlot has a unique ID we can use to identify it in the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("reservations").child(slot.getId());

        // Check for existing reservation
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If there is no existing reservation for this slot, proceed
                if (!dataSnapshot.exists()) {
                    // Create a reservation object
                    Reservation reservation = new Reservation(user.getUid(), slot.getId(), 45);

                    // Push the reservation to the database
                    ref.setValue(reservation)
                            .addOnSuccessListener(aVoid -> {
                                // Update the UI to reflect the new reservation
                                Toast.makeText(BookingActivity.this, "Reservation made successfully!", Toast.LENGTH_SHORT).show();
                                updateSeatAvailability(slot);
                            })
                            .addOnFailureListener(e -> Toast.makeText(BookingActivity.this, "Failed to make reservation: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                } else {
                    Toast.makeText(BookingActivity.this, "This time slot is already reserved.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookingActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSeatAvailability(TimeSlot time) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("buildings").child(building).child("timeSlots").child(time.getId());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Assuming there is a count for indoor and outdoor seats
                long indoorAvailable = (long) dataSnapshot.child("indoorAvailable").getValue();
                long outdoorAvailable = (long) dataSnapshot.child("outdoorAvailable").getValue();

                // Update the TextView with the current availability
                tvSeatAvailability.setText(String.format(Locale.getDefault(), "Seats Available: Indoor - %d, Outdoor - %d", indoorAvailable, outdoorAvailable));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookingActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onTimeSlotClick(TimeSlot timeSlot) {
        // Handle the time slot click event by showing a dialog
        showReservationDialog(timeSlot, building);
    }

    private void showReservationDialog(final TimeSlot timeSlot, String building) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Reservation");
        builder.setMessage("Reserve a seat from " + timeSlot.getStartTime() + " to " + timeSlot.getEndTime() + "?");

        builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Here you would handle the reservation confirmation
                // For example, update the Firebase database with the new reservation
                reserveTimeSlot(timeSlot);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
