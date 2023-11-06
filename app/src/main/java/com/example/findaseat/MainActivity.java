package com.example.findaseat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private GoogleMap myMap;
    private ImageView mapImageView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapImageView = findViewById(R.id.mapImageView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set the "Map" tab as the default selected tab
        bottomNavigationView.setSelectedItemId(R.id.menu_map);
        // Make the ImageView visible by default
        mapImageView.setVisibility(View.VISIBLE);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_profile) {
                    // Handle the Home tab
                    // You can hide the ImageView if it's visible
                    mapImageView.setVisibility(View.GONE);
                    return true;
                } else if (itemId == R.id.menu_map) {
                    // Handle the Map tab
                    // Make the ImageView visible to display the map image
                    mapImageView.setVisibility(View.VISIBLE);
                    return true;
                }
                // Add conditions for other tabs if needed
                return false;
            }
        });
    }
}
