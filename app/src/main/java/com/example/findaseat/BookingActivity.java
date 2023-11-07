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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
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

    private TextView tvBuildingId;
    private RecyclerView rvTimeSlots;

    private List<Integer> indoorSlots;
    private List<Integer> outdoorSlots;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        indoorSlots = new ArrayList<>();
        outdoorSlots = new ArrayList<>();

        //initialize firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("buildings");

        // Retrieve the building ID passed from MainActivity
        Intent intent = getIntent();

        if (intent != null) {
            String buildingId = intent.getStringExtra("BUILDING_ID");
            if (buildingId != null && !buildingId.isEmpty()) {
                // Use the building ID to load the appropriate time slots and prepare the UI
                building = buildingId;
            }
        }
        // Initialize components and layout views
        initializeUI(ref);

    }


    private void initializeUI(DatabaseReference ref) {
        tvBuildingId = (TextView) findViewById(R.id.tvBuildingId);
        rvTimeSlots = findViewById(R.id.rvTimeSlots);

        // Set up the RecyclerView with a LinearLayoutManager and an adapter
        rvTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        fetchTimeSlots();
    }

    //interface for accessing building stuff from backend
    private void fetchTimeSlots() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot indoorSlotsDS = dataSnapshot.child("indoorSlots");

                for (DataSnapshot d : indoorSlotsDS.getChildren()) {
                    Integer num = d.getValue(Integer.class);
                    indoorSlots.add(num);
                }

                DataSnapshot outdoorSlotsDS = dataSnapshot.child("outdoorSlots");

                double open = dataSnapshot.child("open").getValue(Integer.class);

                for (DataSnapshot d : outdoorSlotsDS.getChildren()) {
                    Integer num = d.getValue(Integer.class);
                    outdoorSlots.add(num);
                }

                //after that loop, outdoor Slots and indoorSlots are populated with all slots

                // After fetching data, update the RecyclerView's adapter
                TimeSlotAdapter adapter = (TimeSlotAdapter) rvTimeSlots.getAdapter();


                if (adapter != null) {
                    adapter.setTimeSlots(indoorSlots, outdoorSlots, open);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.out.println("Database Error: " + databaseError.getMessage());
            }
        });
    }


    private void reserveTimeSlot(TimeSlot slot, boolean isIndoor) {
        /*// Check if the user is logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // Not logged in
            Toast.makeText(this, "You must be logged in to make a reservation.", Toast.LENGTH_SHORT).show();
            return;
        }*/

        // Assuming TimeSlot has a unique ID we can use to identify it in the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("buildings").child(building);

        // Check for existing reservation
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // If there is no existing reservation for this slot, proceed
                if (isIndoor) {
                    int currentIndoorSeats = dataSnapshot.child("indoorSlots").child(String.valueOf(slot.getId())).getValue(Integer.class);
                    //indoorSlots.set(slot.getId(), indoorSlots.get(slot.getId()) - 1);
                    dataSnapshot.getRef().child("indoorSlots").child(String.valueOf(slot.getId())).setValue(currentIndoorSeats - 1);

                    //code here for adding timeslot to user in firebase to represent a reservation
                } else {
                    int currentOutdoorSeats = dataSnapshot.child("outdoorSlots").child(String.valueOf(slot.getId())).getValue(Integer.class);

                    //indoorSlots.set(slot.getId(), indoorSlots.get(slot.getId()) - 1);
                    dataSnapshot.getRef().child("outdoorSlots").child(String.valueOf(slot.getId())).setValue(currentOutdoorSeats - 1);

                    //code here for adding timeslot to user in firebase to represent a reservation
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookingActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onTimeSlotClick(TimeSlot timeSlot) {
        //usernameTimeSlots is placeholder for accessing from database
        if (usernameTimeSlots.get(timeSlot.getId()) == null) {
            // Handle the time slot click event by showing a dialog
            showReservationDialog(timeSlot);
        }
        else
        {
            Toast.makeText(this, "You already have a reservation at this time. Please move or cancel it to make a new reservation at this time. ", Toast.LENGTH_SHORT).show();
        }
    }

    private void showReservationDialog(final TimeSlot timeSlot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Reservation");
        builder.setMessage("Select your reservation type:");

        // Check availability and add buttons accordingly
        if (timeSlot.getIndoorSeats() > 0) {
            builder.setPositiveButton("Reserve Indoor", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reserveTimeSlot(timeSlot, true); // true for indoor
                }
            });
        }

        if (timeSlot.getOutdoorSeats() > 0) {
            builder.setNegativeButton("Reserve Outdoor", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reserveTimeSlot(timeSlot, false); // false for outdoor
                }
            });
        }

        // If no seats are available, show a message
        if (timeSlot.getIndoorSeats() <= 0 && timeSlot.getOutdoorSeats() <= 0) {
            builder.setMessage("No seats available for this timeslot.");
            builder.setPositiveButton("OK", null);
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
