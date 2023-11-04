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
import java.util.List;

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

        // Initialize the adapter and set it to the RecyclerView
        buildingAdapter = new BuildingAdapter(buildingList);
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
        buildingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the database is empty and if so, populate it
                if (!dataSnapshot.exists()) {
                    // Your code to create new Building instances and save them
                    // For example:
                    buildingsRef.child("b1").setValue(new Building("b1", "Leavey Library", "Library", 330));
                    buildingsRef.child("b2").setValue(new Building("b2", "Taper Hall", "Lecture Building", 80));
                    buildingsRef.child("b3").setValue(new Building("b3", "Fertitta Hall", "This building is LEED certified. LEED (Leadership in Energy and Environmental Design) is a rating system that provides a framework for healthy, highly efficient, and cost-saving green buildings. Fertitta Hall has a rating of LEED Gold.", 250));
                    buildingsRef.child("b4").setValue(new Building("b4", "Zumberge Hall", "Zumberge Hall is the headquarters for USC’s Department of Earth Sciences as well as the Southern California Earthquake Center.", 60));
                    buildingsRef.child("b5").setValue(new Building("b5", "Waite Phillips Hall", "Waite Phillips Hall of Education is headquarters for the USC Rossier School of Education.", 90));
                    buildingsRef.child("b6").setValue(new Building("b6", "Law Library", "The library primarily supports the teaching and research activities of the USC Law School, but is open and available to all USC faculty, staff and students. The collection consists of nearly 400,000 volumes, including 4,000 serial and periodical titles.", 30));
                    buildingsRef.child("b7").setValue(new Building("b7", "Music Library", "Situated on the ground floor of Doheny Memorial Library, the Music Library's primary mission is to support the research and performance needs of the students, faculty, staff, and alumni of the USC Thornton School of Music.", 330));
                    buildingsRef.child("b8").setValue(new Building("b8", "Tutor Campus Center", "The Ronald Tutor Campus Center is a gathering place, built around International Plaza, at the heart of the University Park campus. It houses the Admission Center and the Epstein Family Alumni Center, along with student offices and boardrooms, study lounges, multipurpose event spaces, technology resources, game and entertainment areas, and a variety of dining options.", 330));
                    buildingsRef.child("b9").setValue(new Building("b9", "Doheny Memorial Library", "Doheny Memorial Library holds vast collections of books and journals and hosts academic and cultural events ranging from lectures, readings and conferences to special exhibits and concerts.", 330));
                    buildingsRef.child("b10").setValue(new Building("b10", "Architecture and Fine Arts Library", "The Helen Topping Architecture and Fine Arts Library houses more than 75,000 volumes and more than 600 videos and DVDs dedicated to the study of art history, fine arts and architecture, as well as a notable collection of rare titles and artists' books.", 330));

                    // Log a message to indicate the buildings have been added
                    Log.d("MainActivity", "Buildings added to Firebase.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error
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
                        buildingList.add(building); // Add to your local list
                    }
                }
                buildingAdapter.notifyDataSetChanged(); // Notify adapter about data change
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error
            }
        });
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Or MainActivity.this.finish();
            }
        });
    }




}