package com.example.findaseat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public boolean isLoggedIn;

    SharedPreferences sharedPreferences;

    private static boolean test = false;

    private MapFragment mapF;

    private LoginFragment loginF;
    private Register registerF;

    private ProfileFragment profileF;

    private static boolean clear = false;

    public static void setTest()
    {
        test = true;
    }

    public static void clear()
    {
        clear = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Hello World");

        sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (test)
        {
            ensureTestUser();
        }
        else if (clear) {
            editor.clear();
            editor.apply();
        }

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
                    boolean[] queries = new boolean[2];

                    //get size of array which is amount of slots, by calculating from open and close time
                    int open = buildingSnapshot.child("open").getValue(Integer.class);
                    int close = buildingSnapshot.child("close").getValue(Integer.class);
                    int slots = (close-open) * 2; //same as dividing by 0.5 bc 30 minute slots

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
                System.out.println("Database Error: " + databaseError.getMessage());
            }
        });
        setContentView(R.layout.activity_main);

        mapF = new MapFragment();

        // Set the default launch screen as the "Map" tab
        loadFragment(mapF, null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_map) {
                loadFragment(mapF, null);
                return true;
            } else if (item.getItemId() == R.id.menu_profile) {
                isLoggedIn = sharedPreferences.getBoolean("loggedIn",false);
                if (isLoggedIn) {
                    Bundle args = new Bundle();
                    String currentUserUscId = sharedPreferences.getString("usc_id", ""); // defaultValue is the default value if the key doesn't exist
                    args.putString("usc_id", currentUserUscId); // Replace with actual USC ID
                    String currentUserName = sharedPreferences.getString("name", "");
                    args.putString("name", currentUserName); // Replace with actual name
                    String currentUserAffiliation = sharedPreferences.getString("affiliation", "");
                    args.putString("affiliation", currentUserAffiliation); // Replace with actual affiliation
                    if (profileF == null)
                    {
                        profileF = new ProfileFragment();
                    }
                    loadFragment(profileF, args);
                } else {
                    if (loginF == null)
                    {
                        loginF = new LoginFragment();
                    }
                    loadFragment(loginF, null);
                }
                return true;
            } else if (item.getItemId() == R.id.menu_registration) {
                if (registerF == null)
                {
                    registerF = new Register();
                }
                loadFragment(registerF, null);
                return true;
            }

            return false;
        });

    }

    private void loadFragment(Fragment fragment, Bundle args) {
        if (fragment instanceof ProfileFragment && isLoggedIn) {
            // If it's a ProfileFragment and the user is logged in, set arguments
            fragment.setArguments(args);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void ensureTestUser()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString("usc_id", "12345678");
        editor.putString("name", "Mantej");
        editor.putString("affiliation", "undergraduate");
        editor.putBoolean("loggedIn", true);
        editor.apply();
    }

}
