package com.example.findaseat;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Register extends Fragment {
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ImageView avatarImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page, container, false);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Find the UI elements in your layout
        EditText emailEditText = view.findViewById(R.id.signup_email);
        EditText uscIDEditText = view.findViewById(R.id.uscid);
        EditText nameEditText = view.findViewById(R.id.signup_name);
        EditText affiliationEditText = view.findViewById(R.id.signup_affiliation);
        EditText passwordEditText = view.findViewById(R.id.idsignup_password);
        Button signupButton = view.findViewById(R.id.signup_button);
        ImageView avatarImageView = view.findViewById(R.id.avatar);

        // Set an OnClickListener for the signupButton
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String email = emailEditText.getText().toString();
                String uscID = uscIDEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String affiliation = affiliationEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                ArrayList<TimeSlot> timeSlots = new ArrayList<>();
                timeSlots.add(new TimeSlot(34, "7:00","7:30", 345, 345 ));

                signup(email, uscID, name, affiliation, password, timeSlots);

                // Upload the avatar (you need to implement this)
                if (imageUri != null) {
                    uploadAvatar(imageUri, uscID);
                }

                // Clear the input fields and display a success message
                clearInputFields(emailEditText, uscIDEditText, nameEditText, affiliationEditText, passwordEditText);
                Toast.makeText(getContext(), "Sign-up successful!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set an OnClickListener for the avatarImageView to choose an avatar image
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image picker (you need to handle image selection)
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        return view;
    }

    // Handle image selection result (you need to implement this)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            // Set the selected image to the avatarImageView
            avatarImageView.setImageURI(imageUri);
        }
    }

    public void signup(String email, String id, String name, String affiliation, String pwd, ArrayList<TimeSlot> timeSlots) {
        // Initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        // Create a User object with the provided data
        User user = new User(email, id, name, affiliation, pwd, timeSlots);

        if (imageUri != null) {
            uploadAvatar(imageUri, id);
        } else {
            // If no image was uploaded, save the user data without an avatar URL
            usersRef.child(id).setValue(user);
        }
    }

    public void uploadAvatar(Uri imageUri, String id) {
        // Create a storage reference for the avatar image
        StorageReference storageRef = storage.getReference().child("avatars/" + id + ".jpg");

        // Upload the image to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Image upload success
                    storageRef.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
                        String downloadUrlStr = downloadUrl.toString();
                        // You can save the download URL to the user's profile in the database
                        // Update the user's profile with the download URL
                        // For example, if you're using Firebase Realtime Database:
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference usersRef = database.getReference("Users");
                        usersRef.child(id).child("avatarUrl").setValue(downloadUrlStr);
                    });
                });
    }

    // Clear input fields
    private void clearInputFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }


    public boolean reservationTest(String user, String building, String location,  String startTime, String endTime) {
        return false;
    }
}
