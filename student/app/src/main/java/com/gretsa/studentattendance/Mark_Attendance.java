package com.gretsa.studentattendance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Locale;

public class Mark_Attendance extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private TextView textViewTime;
    private Button buttonSelectTime;
    private EditText editTextClassDetails;
    private Button buttonMarkAttendance;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marking_attendance);

        textViewTime = findViewById(R.id.textViewTime);
        buttonSelectTime = findViewById(R.id.buttonSelectTime);
        editTextClassDetails = findViewById(R.id.editTextClassDetails);
        buttonMarkAttendance = findViewById(R.id.buttonMarkAttendance);
        firestore = FirebaseFirestore.getInstance();

        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                textViewTime.setText(formattedTime);
            }
        });

        buttonMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classDetails = editTextClassDetails.getText().toString().trim();
                String time = textViewTime.getText().toString().trim();

                if (!classDetails.isEmpty() && !time.isEmpty()) {
                    Attendance attendance = new Attendance(time, classDetails);

                    firestore.collection("attendance")
                            .add(attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Mark_Attendance.this, "Attendance marked successfully", Toast.LENGTH_SHORT).show();
                                    clearFields();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Mark_Attendance.this, "Failed to mark attendance", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(Mark_Attendance.this, "Please enter class details and select time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Request camera permission
        requestCameraPermission();
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void scanQRCode(View view) {
        // Create an instance of IntentIntegrator
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0); // Use the rear camera

        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String scannedText = result.getContents();
            editTextClassDetails.setText(scannedText);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void clearFields() {
        textViewTime.setText("");
        editTextClassDetails.setText("");
    }
}
