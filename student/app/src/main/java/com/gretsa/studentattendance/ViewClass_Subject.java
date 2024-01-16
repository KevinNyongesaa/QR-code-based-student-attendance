package com.gretsa.studentattendance;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewClass_Subject extends AppCompatActivity {

    private TextView textViewClassSubject;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class_subject);

        textViewClassSubject = findViewById(R.id.textViewClassSubject);

        firestore = FirebaseFirestore.getInstance();

        getClassSubjectData();
    }

    private void getClassSubjectData() {
        // Retrieve class and subject data from Firestore
        firestore.collection("class_subject")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ClassSubject> classSubjects = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        ClassSubject classSubject = documentSnapshot.toObject(ClassSubject.class);
                        classSubjects.add(classSubject);
                    }
                    displayClassSubjectData(classSubjects);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ViewClass_Subject.this, "Failed to retrieve class and subject data", Toast.LENGTH_SHORT).show();
                    Log.e("ViewClass_Subject", "Error retrieving class and subject data", e);
                });
    }

    private void displayClassSubjectData(List<ClassSubject> classSubjects) {
        StringBuilder sb = new StringBuilder();
        for (ClassSubject classSubject : classSubjects) {
            String classText = classSubject.getClassText();
            String subjectText = classSubject.getSubjectText();
            sb.append("Class: ").append(classText).append(", Subject: ").append(subjectText).append("\n");
        }
        textViewClassSubject.setText(sb.toString());
    }
}
