package com.gretsa.studentattendance;

import android.content.Intent;
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

public class AdminSignUpActivity extends AppCompatActivity {

    private EditText fullNameEditText, passwordEditText, employeeNumberEditText, departmentEditText;
    private Button signUpButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admins");

        // Get references to UI elements
        fullNameEditText = findViewById(R.id.editTextFullName);
        passwordEditText = findViewById(R.id.editTextPassword);
        employeeNumberEditText = findViewById(R.id.editTextEmployeeNumber);
        departmentEditText = findViewById(R.id.editTextDepartment);
        signUpButton = findViewById(R.id.buttonSignUp);

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform admin sign-up
                String fullName = fullNameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String employeeNumber = employeeNumberEditText.getText().toString().trim();
                String department = departmentEditText.getText().toString().trim();

                // Validate the entered data
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(AdminSignUpActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(AdminSignUpActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(employeeNumber)) {
                    Toast.makeText(AdminSignUpActivity.this, "Please enter your employee number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(department)) {
                    Toast.makeText(AdminSignUpActivity.this, "Please enter your department", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new admin account in Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(fullName + "@example.com", password)
                        .addOnCompleteListener(AdminSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Admin sign-up successful
                                    String adminId = firebaseAuth.getCurrentUser().getUid();
                                    Admin admin = new Admin(adminId, fullName, employeeNumber, department);
                                    // Save the admin details in Firebase Realtime Database
                                    databaseReference.child(adminId).setValue(admin);

                                    Toast.makeText(AdminSignUpActivity.this, "Admin sign-up successful", Toast.LENGTH_SHORT).show();
                                    // Redirect to the admin menu or perform admin-specific tasks
                                    // For example, you can start a new activity:
                                    startActivity(new Intent(AdminSignUpActivity.this, AdminLogin.class));
                                    finish();
                                } else {
                                    // Admin sign-up failed
                                    Toast.makeText(AdminSignUpActivity.this, "Admin sign-up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
