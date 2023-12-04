package com.example.findaseat;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private ArrayAdapter<TimeSlot> adapter;

    private ValueEventListener indoorSlotsListener;
    private ValueEventListener outdoorSlotsListener;


    private String building;

    private TextView tvBuildingName;

    private static HashMap<Integer, TimeSlot> usernameTimeSlots = new HashMap<>();
    private RecyclerView rvTimeSlots;

    private List<Integer> indoorSlots;
    private List<Integer> outdoorSlots;

    private String buildingName;

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

            ref.child("buildingName").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = (String)dataSnapshot.getValue();
                    buildingName = getFirstWord(name);
                }

                @Override
                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                    Log.e("BookingFragment", "Failed to read time slots", error.toException());
                }
            });

            usrdb = database.getReference("Users").child(usc_id);
        }
        // Initialize components and layout views
        initializeUI(view);
        return view;

    }

    //gets first word of a string --> for building name
    private String getFirstWord(String text) {
        int index = text.indexOf(' ');
        if (index > -1) { // Check if there is more than one word.
            return text.substring(0, index).trim(); // Extract first word.
        } else {
            return text; // Text is the first word itself.
        }
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
                        //this is the seats per slot available
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
                        ref.child("close").get().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                Integer close = task2.getResult().getValue(Integer.class);
                                if (close != null) {
                                    adapter.setTimeSlots(indoorSlots, outdoorSlots, open, close, building, buildingName);
                                }
                            } else {
                                Log.e("firebase", "Error getting 'open' value", task.getException());
                            }
                        });
                    }
                } else {
                    Log.e("firebase", "Error getting 'open' value", task.getException());
                }
            });
        }
    }


    private void reserveTimeSlot(TimeSlot slot, boolean isIndoor) {
        // Assuming TimeSlot has a unique ID we can use to identify it in the database
        String slotType = isIndoor ? "indoorSlots" : "outdoorSlots";
        DatabaseReference slotTypeRef = ref.child(slotType).child(String.valueOf(slot.getId()));
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


    @Override
    public void onReserveButtonClick(TimeSlot selectedTimeSlot) {
        if (isLoggedIn)
        {
            DatabaseReference userTimeslotsRef = usrdb.child("timeSlots");

            userTimeslotsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count = 0;
                    boolean consecutive = false;
                    //start at false so only one true needed to make true

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        count++;//have to go through entire loop to get it
                        TimeSlot existingTimeSlot = snapshot.getValue(TimeSlot.class);

                        //need to check are consecutive at same building
                        if (existingTimeSlot != null) {
                            if ((selectedTimeSlot.getEndTime().equals(existingTimeSlot.getStartTime()) ||
                                    selectedTimeSlot.getStartTime().equals(existingTimeSlot.getEndTime())) && selectedTimeSlot.getBuilding().equals(existingTimeSlot.getBuilding())) {
                                // This means the selected time slot is immediately before or after an existing reservation
                                consecutive = true;
                            }
                        }
                    }

                    //if is consecutive and count <= 4, or is first iteration, then can reserve
                    if (consecutive && count < 4 || count == 0) {
                        showReservationDialog(selectedTimeSlot);
                    }
                    else //everything else is can't
                    {
                        Toast.makeText(getContext(), "You can only reserve 4 consecutive time slots in the same building.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("firebase", "Error checking timeslot", databaseError.toException());
                }
            });

        }
        else
        {
            Toast.makeText(getContext(), "Please login to make a reservation!", Toast.LENGTH_SHORT).show();
        }
    }


    private void showReservationDialog(final TimeSlot timeSlot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

