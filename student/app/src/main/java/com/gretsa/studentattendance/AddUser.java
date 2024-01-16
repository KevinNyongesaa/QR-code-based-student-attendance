package com.gretsa.studentattendance;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUser extends AppCompatActivity {

    private EditText fullNameEditText, passwordEditText, courseEditText, regNumberEditText;
    private Button signUpButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        // Initialize Firebase Authentication and Realtime Database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Get references to UI elements
        fullNameEditText = findViewById(R.id.editTextFullName);
        passwordEditText = findViewById(R.id.editTextPassword);
        courseEditText = findViewById(R.id.editTextCourse);
        regNumberEditText = findViewById(R.id.editTextDepartment);
        signUpButton = findViewById(R.id.buttonSignUp);

        // Set click listener for the sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform user sign up
                String fullName = fullNameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String course = courseEditText.getText().toString().trim();
                String regNumber = regNumberEditText.getText().toString().trim();

                // Validate the entered data
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(AddUser.this, "Please enter the user's full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(AddUser.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(course)) {
                    Toast.makeText(AddUser.this, "Please enter the user's course", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(regNumber)) {
                    Toast.makeText(AddUser.this, "Please enter the user's registration number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create the user account in Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(fullName + "@example.com", password)
                        .addOnCompleteListener(AddUser.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User account creation successful
                                    String userId = firebaseAuth.getCurrentUser().getUid();

                                    // Save user details in the Firebase Realtime Database
                                    DatabaseReference userRef = databaseReference.child(userId);
                                    userRef.child("fullName").setValue(fullName);
                                    userRef.child("course").setValue(course);
                                    userRef.child("regNumber").setValue(regNumber);
                                    userRef.child("isAdmin").setValue(false);

                                    Toast.makeText(AddUser.this, "User added successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    // User account creation failed
                                    Toast.makeText(AddUser.this, "Failed to add user: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
