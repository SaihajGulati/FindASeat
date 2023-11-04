package com.example.findaseat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking); // Set this to your layout XML for BookingActivity

        // Set up the ActionBar's home button for navigation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set up the back button
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will simulate the hardware back button being pressed
                onBackPressed();
            }
        });
    }

    // Override the onBackPressed method
    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Default action on back pressed
        // Optionally, add any additional code if needed
        // For example, if you want to add animations or log analytics events.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();  // Call the back button's functionality
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}