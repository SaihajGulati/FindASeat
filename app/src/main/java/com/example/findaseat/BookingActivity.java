package com.example.findaseat;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.view.MenuItem;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking); // Set this to your layout XML for BookingActivity

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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
