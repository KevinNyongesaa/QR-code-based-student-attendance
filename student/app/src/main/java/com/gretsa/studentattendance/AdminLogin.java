package com.gretsa.studentattendance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView, signupTextView;
    private ImageView logoImageView, backgroundImageView;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference adminRef;
    private DatabaseReference studentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        // Get reference to the "admins" child in the Firebase database
        adminRef = FirebaseDatabase.getInstance().getReference("admins");
        // Get reference to the "students" child in the Firebase database
        studentRef = FirebaseDatabase.getInstance().getReference("students");

        // Get references to UI elements
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        forgotPasswordTextView = findViewById(R.id.textViewForgotPassword);
        signupTextView = findViewById(R.id.textViewSignup);
        logoImageView = findViewById(R.id.imageViewLogo);
        backgroundImageView = findViewById(R.id.imageViewBackground);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLogin();
            }
        });

        // Set click listener for the "Forgot Password?" text
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this, Forgot_Password.class));
                Toast.makeText(AdminLogin.this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Set click listener for the "Sign up" text
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLogin.this, AdminSignUpActivity.class));
            }
        });
    }

    private void adminLogin() {
        // Get the entered username and password
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate the entered credentials
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Authenticate the user using Firebase Authentication
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User login successful
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            // Check if the user is an admin or student
                            adminRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        // Admin login successful
                                        Toast.makeText(AdminLogin.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                                        // Redirect to the admin menu or perform admin-specific tasks
                                        // For example, you can start a new activity:
                                        startActivity(new Intent(AdminLogin.this, Admin_Menu.class));
                                        finish();
                                    } else {
                                        // User is not an admin
                                        Toast.makeText(AdminLogin.this, "You are not authorized as an admin", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut(); // Sign out the user
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle database error
                                    Toast.makeText(AdminLogin.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // User login failed
                            Toast.makeText(AdminLogin.this, "Admin login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
