package com.example.findaseat;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BuildingDetailActivity extends AppCompatActivity {

    private TextView buildingNameTextView;
    private TextView buildingDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildingNameTextView = findViewById(R.id.buildingName); // Ensure you have a TextView with this ID in your layout

    }
}
