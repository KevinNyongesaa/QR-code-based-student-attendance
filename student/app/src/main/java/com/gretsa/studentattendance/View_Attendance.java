package com.gretsa.studentattendance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class View_Attendance extends AppCompatActivity {

    private static final String TAG = "View_Attendance";

    private TextView textViewClass;
    private TextView textViewSubject;
    private EditText editTextRegistrationNumber;
    private Button buttonViewAttendance;
    private TextView textViewAttendance;

    private FirebaseFirestore firestore;

    private String classText;
    private String subjectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        textViewClass = findViewById(R.id.textViewClass);
        textViewSubject = findViewById(R.id.textViewSubject);
        editTextRegistrationNumber = findViewById(R.id.editTextRegistrationNumber);
        buttonViewAttendance = findViewById(R.id.buttonViewAttendance);
        textViewAttendance = findViewById(R.id.textViewAttendance);

        classText = getIntent().getStringExtra("class");
        subjectText = getIntent().getStringExtra("subject");

        textViewClass.setText(classText);
        textViewSubject.setText(subjectText);

        firestore = FirebaseFirestore.getInstance();

        buttonViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registrationNumber = editTextRegistrationNumber.getText().toString().trim();
                if (!registrationNumber.isEmpty()) {
                    retrieveAttendance(registrationNumber);
                }
            }
        });
    }

    private void retrieveAttendance(String registrationNumber) {
        firestore.collection("attendance")
                .document(registrationNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            Attendance attendance = task.getResult().toObject(Attendance.class);
                            if (attendance != null) {
                                String time = attendance.getTime();
                                String retrievedRegistrationNumber = attendance.getRegistrationNumber();

                                textViewAttendance.setText(String.format("Time: %s\nRegistration Number: %s", time, retrievedRegistrationNumber));

                                calculateAttendancePercentage(registrationNumber);
                            } else {
                                textViewAttendance.setText("No attendance recorded for the given registration number.");
                            }
                        } else {
                            textViewAttendance.setText("No attendance recorded for the given registration number.");
                        }
                    } else {
                        Log.e(TAG, "Error retrieving attendance document: ", task.getException());
                    }
                });
    }

    private void calculateAttendancePercentage(String registrationNumber) {
        firestore.collection("attendance")
                .whereEqualTo("registrationNumber", registrationNumber)
                .whereEqualTo("classSubject", classText)
                .whereEqualTo("course", subjectText)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            int totalAttendanceCount = querySnapshot.size();
                            int totalClassesCount = getTotalClassesCount();

                            double attendancePercentage = (double) totalAttendanceCount / totalClassesCount * 100;

                            String attendanceMessage = String.format("Attendance: %.2f%%", attendancePercentage);
                            textViewAttendance.append("\n" + attendanceMessage);
                        }
                    } else {
                        Log.e(TAG, "Error getting attendance documents: ", task.getException());
                    }
                });
    }

    private int getTotalClassesCount() {
        // You can implement your logic here to retrieve the total number of classes for the given classText and subjectText.
        // This could be from a separate collection or any other data source.

        // For now, let's assume a fixed value of 20 classes.
        return 20;
    }
}
