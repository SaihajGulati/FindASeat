package com.example.findaseat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//
//public class BookingActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking); // Set this to your layout XML for BookingActivity
//
//        // Set up the ActionBar's home button for navigation
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//
//        // Set up the back button
//        Button backButton = findViewById(R.id.btn_back);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // This will simulate the hardware back button being pressed
//                onBackPressed();
//            }
//        });
//    }
//
//    // Override the onBackPressed method
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();  // Default action on back pressed
//        // Optionally, add any additional code if needed
//        // For example, if you want to add animations or log analytics events.
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();  // Call the back button's functionality
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}

public class BookingActivity extends AppCompatActivity {

    private TextView buildingNameTextView;
    private TextView buildingDescriptionTextView;
    private Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        buildingNameTextView = findViewById(R.id.buildingNameTextView);
        buildingDescriptionTextView = findViewById(R.id.buildingDescriptionTextView);
        TextView numberOfSeatsView = findViewById(R.id.numberOfSeats);

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

}
