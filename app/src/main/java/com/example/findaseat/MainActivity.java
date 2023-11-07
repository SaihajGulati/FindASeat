package com.example.findaseat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Hello World");

        // Get a reference to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("buildings");

// Read the buildings data from the database
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot buildingSnapshot : dataSnapshot.getChildren()) {
                    // Get the current number of indoor and outdoor seats for each building
                    int indoorSeats = buildingSnapshot.child("indoorSeats").getValue(Integer.class);
                    int outdoorSeats = buildingSnapshot.child("outdoorSeats").getValue(Integer.class);

                    //get size of array which is amount of slots, by calculating from open and close time
                    int open = buildingSnapshot.child("open").getValue(Integer.class);
                    int close = buildingSnapshot.child("close").getValue(Integer.class);
                    int slots = (close-open) * 2; //same as dividing by 0.5 bc 30 minute slots

                    // Create lists for indoorSlots and outdoorSlots
                    //each slot is a 30 min time period for that building, and how many seats are left
                    //start it with size of slots and then number of indoor/outdoorseats as starting value in each slot
                    //split up by indoor and outdoor
                    List<Integer> indoorSlots = new ArrayList<>(Collections.nCopies(slots, indoorSeats));
                    List<Integer> outdoorSlots = new ArrayList<>(Collections.nCopies(slots, outdoorSeats));

                    // Update the current building with the new fields
                    buildingSnapshot.getRef().child("indoorSlots").setValue(indoorSlots);
                    buildingSnapshot.getRef().child("outdoorSlots").setValue(outdoorSlots);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.out.println("Database Error: " + databaseError.getMessage());
            }
        });
        Log.d("TEST", "HI");
        setContentView(R.layout.activity_main);

        // Set the default launch screen as the "Map" tab
        loadFragment(new MapFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_map) {
                loadFragment(new MapFragment());
                return true;
            } else if (item.getItemId() == R.id.menu_profile) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
