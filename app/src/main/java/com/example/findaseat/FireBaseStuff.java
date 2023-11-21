package com.example.findaseat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireBaseStuff {
    public static void main(String[] args) {
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





    }







}
