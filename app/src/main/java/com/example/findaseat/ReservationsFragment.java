package com.example.findaseat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment implements TimeSlotAdapterShow.OnButtonClickListener{

    DatabaseReference usrdb;

    DatabaseReference ref;
    String usc_id;

    public ReservationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a FirebaseDatabase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Access the SharedPreferences once the Fragment is safely created and has a context.
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        usc_id = sharedPreferences.getString("usc_id", "");

        // Get a DatabaseReference to the user's data
        usrdb = database.getReference("Users").child(usc_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reservations_fragment, container, false);
    }

    private void fetchTimeSlots() {
        // Reference to your RecyclerView in the layout
        RecyclerView recyclerView = getView().findViewById(R.id.rvTimeSlots);

        // Set up the RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize your list of time slots
        List<TimeSlot> timeSlots = new ArrayList<>();

        // Create an adapter for the RecyclerView with the list of time slots
        TimeSlotAdapterShow adapter = new TimeSlotAdapterShow(timeSlots, this);
        recyclerView.setAdapter(adapter);

        // Fetch the time slots from Firebase
        usrdb.child("timeSlots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timeSlots.clear();
                // Iterate over the snapshots and add them to your list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimeSlot timeSlot = snapshot.getValue(TimeSlot.class);
                    timeSlots.add(timeSlot);
                }

                // Notify the adapter that the data has changed so the UI can be updated
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                Log.e("ReservationsFragment", "Failed to read time slots", error.toException());
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Now it's safe to call fetchTimeSlots because the view hierarchy is fully created.
        fetchTimeSlots();
    }

    @Override
    public void onChangeClick(TimeSlot timeSlot) {
        showReservationDialog(timeSlot, "change");
    }

    @Override
    public void onCancelClick(TimeSlot timeSlot) {
        showReservationDialog(timeSlot, "cancel");
    }

    private void showReservationDialog(final TimeSlot timeSlot, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Would you like to change or cancel your reservation?");

        // Check availability and add buttons accordingly
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelReservation(timeSlot);
                Toast.makeText(getContext(), "Old Reservation Cancelled. Please select a building to begin booking another", Toast.LENGTH_SHORT).show();
                    navigateToMainMap();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelReservation(timeSlot);
                Toast.makeText(getContext(), "Reservation Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*private void showReservationDialog(final TimeSlot timeSlot, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure you would like to " + type + " your reservation?");

        // Check availability and add buttons accordingly
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelReservation(timeSlot);
                if (type.equals("cancel"))
                {
                    Toast.makeText(getContext(), "Reservation Cancelled", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Old Reservation Cancelled. Please select a building to begin booking another", Toast.LENGTH_SHORT).show();
                    navigateToMainMap();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

    private void navigateToMainMap() {
        // Ensure that we are attached to an Activity and the FragmentManager is not null
        if (isAdded() && getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // Replace your current fragment with the MainMapFragment
            transaction.replace(R.id.fragmentContainer, new MapFragment()); // Use the correct container ID

            // Add transaction to backstack if you want to navigate back
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }


    public void cancelReservation(TimeSlot timeSlot)
    {
        // Check if the timeslot is already taken by querying the Firebase database
        DatabaseReference userTimeslotsRef = usrdb.child("timeSlots");

        //get the key of the part of the data where
        //String key = userTimeslotsRef.orderByChild("startTime").equalTo(timeSlot.getStartTime()).get()

        userTimeslotsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Here you can get the value of each child under 'timeSlots'
                    TimeSlot ts = childSnapshot.getValue(TimeSlot.class);
                    if (ts != null && timeSlot.getStartTime().equals(ts.getStartTime())) {
                        String key = childSnapshot.getKey();
                        userTimeslotsRef.child(key).removeValue();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting the data failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
}

