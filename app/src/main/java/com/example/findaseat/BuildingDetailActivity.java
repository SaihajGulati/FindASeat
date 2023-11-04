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
//        setContentView(R.layout.activity_building_detail); // Ensure you have a layout file named activity_building_detail.xml

        buildingNameTextView = findViewById(R.id.buildingName); // Ensure you have a TextView with this ID in your layout
////        buildingDescriptionTextView = findViewById(R.id.buildingDescription); // Ensure you have a TextView with this ID in your layout
//
//        // Retrieve the building details from the Intent that started this Activity
//        if (getIntent().hasExtra("BUILDING_NAME") && getIntent().hasExtra("BUILDING_DESCRIPTION")) {
//            String buildingName = getIntent().getStringExtra("BUILDING_NAME");
//            String buildingDescription = getIntent().getStringExtra("BUILDING_DESCRIPTION");
//
//            // Set the building details to the TextViews
//            buildingNameTextView.setText(buildingName);
//            buildingDescriptionTextView.setText(buildingDescription);
//        } else {
//            // Handle the case where no building details were provided
//            buildingNameTextView.setText("No Building Name Provided");
//            buildingDescriptionTextView.setText("No Building Description Provided");
//        }
    }
}
