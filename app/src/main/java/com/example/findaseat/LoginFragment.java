package com.example.findaseat;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;

public class LoginFragment extends Fragment {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        // Find UI elements
        emailEditText = view.findViewById(R.id.login_email);
        passwordEditText = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.login_button);

        // Set an OnClickListener for the loginButton
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Call a login method to authenticate the user
            loginUser(email, password);
        });

        return view;
    }

    private void loginUser(String email, String password) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String storedPassword = userSnapshot.child("pwd").getValue(String.class);
                        if (storedPassword.equals(password)) {
                            // Password matches, user is successfully logged in
                            String uscId = userSnapshot.child("usc_id").getValue(String.class);
                            String name = userSnapshot.child("name").getValue(String.class);
                            String affiliation = userSnapshot.child("affiliation").getValue(String.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("usc_id", uscId);
                            bundle.putString("name", name);
                            bundle.putString("affiliation", affiliation);

                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("usc_id", uscId);
                            editor.putString("name", name);
                            editor.putString("affiliation", affiliation);
                            editor.putBoolean("loggedIn", true);
                            editor.apply();

                            ProfileFragment profileFragment = new ProfileFragment();
                            profileFragment.setArguments(bundle);

                            // Replace the current fragment with the ProfileFragment
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainer, profileFragment)
                                    .addToBackStack(null)
                                    .commit();

                        } else {
                            // Password doesn't match, show an error message
                            Toast.makeText(getContext(), "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // User with the provided email doesn't exist, show an error message
                    Toast.makeText(getContext(), "User not found. Please check your email.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors or show a message in case of data retrieval failure
                Toast.makeText(getContext(), "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
