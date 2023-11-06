package com.example.findaseat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.findaseat.BookingActivity;

import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.findaseat.BuildingData;

public class MainActivity extends AppCompatActivity {

    private RecyclerView buildingsRecyclerView;
    private BuildingAdapter buildingAdapter;
    private List<Building> buildingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference buildingsRef = database.getReference("buildings");

        buildingsRecyclerView = findViewById(R.id.buildingsRecyclerView); // make sure you have a RecyclerView with this id in your layout
        buildingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create Building instances and add them to the buildingList
        populateBuildingData(buildingsRef);

        buildingAdapter = new BuildingAdapter(this, buildingList);
        buildingsRecyclerView.setAdapter(buildingAdapter);

        // Setup the booking button if it's in activity_main.xml
        Button bookingButton = findViewById(R.id.btn_booking);
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });


        Button backButton = findViewById(R.id.btn_back);
        if (backButton != null) {
            backButton.setVisibility(View.GONE);
        }


    }

    private void populateBuildingData(DatabaseReference buildingsRef) {
        // Create new Building instances with the new structure and set them
        Map<String, Object> buildingsUpdates = new HashMap<>();
        buildingsUpdates.put("b1", new Building("b1", "Leavey Library", "Library", 330, 800, 1700));
        buildingsUpdates.put("b2", new Building("b2", "Taper Hall", "Lecture Building", 80, 800, 1700));
        buildingsUpdates.put("b3", new Building("b3", "Fertitta Hall", "This building is LEED certified...", 250, 800, 1700));
        buildingsUpdates.put("b4", new Building("b4", "Zumberge Hall", "Zumberge Hall is the headquarters for USC’s Department of Earth Sciences as well as the Southern California Earthquake Center.", 60, 800, 1600));
        buildingsUpdates.put("b5", new Building("b5", "Waite Phillips Hall", "Waite Phillips Hall of Education is headquarters for the USC Rossier School of Education.", 90, 1000, 1800));
        buildingsUpdates.put("b6", new Building("b6", "Law Library", "The library primarily supports the teaching and research activities of the USC Law School, but is open and available to all USC faculty, staff and students. The collection consists of nearly 400,000 volumes, including 4,000 serial and periodical titles.", 30, 1200,1700));
        buildingsUpdates.put("b7", new Building("b7", "Music Library", "Situated on the ground floor of Doheny Memorial Library, the Music Library's primary mission is to support the research and performance needs of the students, faculty, staff, and alumni of the USC Thornton School of Music.", 330, 1100, 1900));
        buildingsUpdates.put("b8", new Building("b8", "Tutor Campus Center", "The Ronald Tutor Campus Center is a gathering place, built around International Plaza, at the heart of the University Park campus. It houses the Admission Center and the Epstein Family Alumni Center, along with student offices and boardrooms, study lounges, multipurpose event spaces, technology resources, game and entertainment areas, and a variety of dining options.", 330, 1000, 1800));
        buildingsUpdates.put("b9", new Building("b9", "Doheny Memorial Library", "Doheny Memorial Library holds vast collections of books and journals and hosts academic and cultural events ranging from lectures, readings and conferences to special exhibits and concerts.", 330, 800, 1900));
        buildingsUpdates.put("b10", new Building("b10", "Architecture and Fine Arts Library", "The Helen Topping Architecture and Fine Arts Library houses more than 75,000 volumes and more than 600 videos and DVDs dedicated to the study of art history, fine arts and architecture, as well as a notable collection of rare titles and artists' books.", 330, 900, 300));




        buildingsRef.setValue(buildingsUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("MainActivity", "All buildings data updated with new structure.");
            } else {
                Log.w("MainActivity", "Failed to update buildings data.", task.getException());
            }
        });

        // Now, independently set up the ValueEventListener to update the UI
        buildingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buildingList.clear(); // Clear existing data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Building building = snapshot.getValue(Building.class);
                    if (building != null) {
                        buildingList.add(building); // This now includes open and close times
                    }
                }
                buildingAdapter.notifyDataSetChanged(); // Notify adapter about data change
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error
            }
        });

    }



}