package com.example.findaseat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private ImageView userPhotoImageView;
    private TextView uscIdTextView;
    private TextView nameTextView;
    private TextView affiliationTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile, container, false);

        // Find UI elements in the fragment layout
        userPhotoImageView = view.findViewById(R.id.user_photo);
        uscIdTextView = view.findViewById(R.id.user_usc_id);
        nameTextView = view.findViewById(R.id.user_name);
        affiliationTextView = view.findViewById(R.id.user_affiliation);

        // Retrieve user data from arguments
        Bundle args = getArguments();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);

        Boolean isLoggedIn = sharedPreferences.getBoolean("loggedIn", false);

        if (isLoggedIn) {
            String uscId = sharedPreferences.getString("usc_id", null); // defaultValue is the default value if the key doesn't exist
            String name = sharedPreferences.getString("name", null);
            String affiliation = sharedPreferences.getString("affiliation", null);

            // Set user information in the TextViews
            uscIdTextView.setText("USC ID: " + uscId);
            nameTextView.setText("Name: " + name);
            affiliationTextView.setText("Affiliation: " + affiliation);

            // Retrieve and load the user's photo
            loadUserPhoto(uscId);
        }

        Button showReservationsButton = view.findViewById(R.id.showReservationsButton);
        showReservationsButton.setOnClickListener(v -> {
            // Create an instance of the ReservationsFragment
            ReservationsFragment reservationsFragment = new ReservationsFragment();

            // Begin a FragmentTransaction and replace the current fragment with reservationsFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, reservationsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
                logout();
            } else {
                Toast.makeText(getActivity(), "Logged out not successful", Toast.LENGTH_SHORT).show();
            }

        });

        return view;

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
                // Handle errors or show a message in case of data retrieval failure
            }
        });
    }

    public void logout() {
        if (getView() == null) {
            // Handle the case where the fragment's view is null
            return;
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        LoginFragment loginFragment = new LoginFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, loginFragment)
                .addToBackStack(null)  // Optional: Add to back stack for navigation history
                .commit();

        Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }
}
