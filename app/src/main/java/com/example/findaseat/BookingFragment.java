package com.example.findaseat;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingFragment extends Fragment implements TimeSlotAdapter.OnReserveButtonClickListener {

    private BottomNavigationView bottomNavigationView;
    private ListView lvTimeSlots;
    private TextView tvBuildingDescription;
    private TextView tvSeatAvailability;

    // A list to hold time slots for display
    private ArrayList<Boolean> timeSlotsList;
    private ArrayAdapter<TimeSlot> adapter;

    private ValueEventListener indoorSlotsListener;
    private ValueEventListener outdoorSlotsListener;


    private String building;

    private TextView tvBuildingName;

    private static HashMap<Integer, TimeSlot> usernameTimeSlots = new HashMap<>();
    private RecyclerView rvTimeSlots;

    private List<Integer> indoorSlots;
    private List<Integer> outdoorSlots;

    DatabaseReference ref;

    DatabaseReference usrdb;
    Boolean isLoggedIn;

    String usc_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        /*// Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button in the Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        indoorSlots = new ArrayList<>();
        outdoorSlots = new ArrayList<>();

        //initialize firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Retrieve the building ID passed from MainActivity
        Bundle args = getArguments();

        if (args != null) {
            building = args.getString("BUILDING_ID");

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
            usc_id = sharedPreferences.getString("usc_id", ""); // defaultValue is the default value if the key doesn't exist
            isLoggedIn = sharedPreferences.getBoolean("loggedIn", false);


            ref = database.getReference("buildings").child(building);
            usrdb = database.getReference("Users").child(usc_id);
        }
        // Initialize components and layout views
        initializeUI(view);
        return view;

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void initializeUI(View view) {

        tvBuildingDescription =  view.findViewById(R.id.tvBuildingDescription);
        tvBuildingName =  view.findViewById(R.id.tvBuildingName);
        rvTimeSlots = view.findViewById(R.id.rvTimeSlots);

        ref.child("description").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tvBuildingDescription.setText(task.getResult().getValue(String.class));
            } else {
                Log.e("firebase", "Error getting data", task.getException());
            }
        });

        ref.child("buildingName").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tvBuildingName.setText(task.getResult().getValue(String.class));
            } else {
                Log.e("firebase", "Error getting data", task.getException());
            }
        });
        // Initialize adapter
        TimeSlotAdapter adapter = new TimeSlotAdapter(this);

        // Set the LayoutManager
        rvTimeSlots.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the adapter to the RecyclerView
        rvTimeSlots.setAdapter(adapter);

        fetchTimeSlots();
    }


    private void fetchTimeSlots() {
        ValueEventListener indoorSlotsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                indoorSlots.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Integer num = d.getValue(Integer.class);
                    if (num != null) {
                        indoorSlots.add(num);
                    }
                }
                updateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("firebase", "Error getting indoor slots data", databaseError.toException());
            }
        };

        ValueEventListener outdoorSlotsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                outdoorSlots.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Integer num = d.getValue(Integer.class);
                    if (num != null) {
                        outdoorSlots.add(num);
                    }
                }
                Log.d("outdoorslots", outdoorSlots.toString());
                updateRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("firebase", "Error getting outdoor slots data", databaseError.toException());
            }
        };

        // Attach the listeners to start listening for real-time updates
        ref.child("indoorSlots").addValueEventListener(indoorSlotsListener);
        ref.child("outdoorSlots").addValueEventListener(outdoorSlotsListener);
    }

    private void updateRecyclerView() {
        // Notify the RecyclerView's adapter to update the views.
        TimeSlotAdapter adapter = (TimeSlotAdapter) rvTimeSlots.getAdapter();
        if (adapter != null) {
            ref.child("open").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Integer open = task.getResult().getValue(Integer.class);
                    if (open != null) {
                        Log.d("OPEN", open.toString());
                        adapter.setTimeSlots(indoorSlots, outdoorSlots, open);
                    }
                } else {
                    Log.e("firebase", "Error getting 'open' value", task.getException());
                }
            });
        }
    }


    private void reserveTimeSlot(TimeSlot slot, boolean isIndoor) {
        // Assuming TimeSlot has a unique ID we can use to identify it in the database
        DatabaseReference slotRef = FirebaseDatabase.getInstance().getReference("buildings").child(building);
        String slotType = isIndoor ? "indoorSlots" : "outdoorSlots";
        timeSlotsList.add(true);
        DatabaseReference slotTypeRef = slotRef.child(slotType).child(String.valueOf(slot.getId()));
        if (isLoggedIn)
        {
            slotTypeRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Integer currentSeats = task.getResult().getValue(Integer.class);
                    if (currentSeats != null && currentSeats > 0) {
                        DatabaseReference userTimeslotsRef = usrdb.child("timeSlots");
                        userTimeslotsRef.push().setValue(slot, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    // Successfully added the timeslot to the user's list
                                    Toast.makeText(getContext(), "Time slot reserved successfully!", Toast.LENGTH_SHORT).show();
                                    // Decrement the seat count
                                    slotTypeRef.setValue(currentSeats - 1);
                                } else {
                                    // Failed to add the timeslot to the user's list
                                    Log.e("firebase", "Failed to add timeslot to user's list", databaseError.toException());
                                    Toast.makeText(getContext(), "Failed to reserve time slot.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // Handle the case where there are no seats available
                        Toast.makeText(getContext(), "No seats available.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "Please login to make a reservation!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onReserveButtonClick(TimeSlot timeSlot) {
        // Check if the timeslot is already taken by querying the Firebase database
        DatabaseReference userTimeslotsRef = usrdb.child("timeSlots");
        timeSlotsList.add(true);

        userTimeslotsRef.orderByChild("startTime").equalTo(timeSlot.getStartTime()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // If the dataSnapshot has children, it means the timeslot is already taken
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    Toast.makeText(getContext(), "You already have a reservation at this time.", Toast.LENGTH_SHORT).show();


                } else {
                    // If the dataSnapshot does not exist or is empty, the timeslot is not taken
                    // Proceed with reservation
                    showReservationDialog(timeSlot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                Log.e("firebase", "Error checking timeslot", databaseError.toException());
            }
        });
    }

    private void showReservationDialog(final TimeSlot timeSlot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        timeSlotsList.add(true);
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

