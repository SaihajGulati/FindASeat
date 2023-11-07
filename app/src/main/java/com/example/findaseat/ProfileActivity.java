package com.example.findaseat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private ImageView userPhotoImageView;
    private TextView uscIdTextView;
    private TextView nameTextView;
    private TextView affiliationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        // Find UI elements
        userPhotoImageView = findViewById(R.id.user_photo);
        uscIdTextView = findViewById(R.id.user_usc_id);
        nameTextView = findViewById(R.id.user_name);
        affiliationTextView = findViewById(R.id.user_affiliation);

        // Retrieve user data from the intent extras
        String uscId = getIntent().getStringExtra("usc_id");
        String name = getIntent().getStringExtra("name");
        String affiliation = getIntent().getStringExtra("affiliation");

        // Set user information in the TextViews
        uscIdTextView.setText("USC ID: " + uscId);
        nameTextView.setText("Name: " + name);
        affiliationTextView.setText("Affiliation: " + affiliation);

        // Retrieve and load the user's photo
        loadUserPhoto(uscId);

        BottomNavigationView bottomNavigationView = findViewById(R.id.normalNav);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
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

    private void loadUserPhoto(String uscId) {
        // Retrieve the user's photo URL from Firebase Realtime Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(uscId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String photoUrl = dataSnapshot.child("avatarUrl").getValue(String.class);

                    // Use Picasso to load the user's photo into the userPhotoImageView
                    Picasso.get().load(photoUrl)
                            .placeholder(R.drawable.default_avatar)
                            .error(R.drawable.default_avatar)
                            .into(userPhotoImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Inside the loadFragment method in ProfileActivity
    private void loadFragment(Fragment fragment) {
        if (fragment instanceof ProfileFragment) {
            // Pass user data to the ProfileFragment
            String uscId = getIntent().getStringExtra("usc_id");
            String name = getIntent().getStringExtra("name");
            String affiliation = getIntent().getStringExtra("affiliation");
            ProfileFragment profileFragment = ProfileFragment.newInstance(uscId, name, affiliation);
            fragment = profileFragment;
            setMenuItemChecked(R.id.menu_profile);
        } else if (fragment instanceof MapFragment) {
            setMenuItemChecked(R.id.menu_map);
        }

        // Rest of the code to load the fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setMenuItemChecked(int menuItemId) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.normalNav);
        bottomNavigationView.getMenu().findItem(menuItemId).setChecked(true);
    }

}
