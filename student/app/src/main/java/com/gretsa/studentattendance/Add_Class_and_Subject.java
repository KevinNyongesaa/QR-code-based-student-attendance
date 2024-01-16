package com.gretsa.studentattendance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Add_Class_and_Subject extends AppCompatActivity {

    private EditText editTextClass;
    private EditText editTextSubject;
    private Button buttonAdd;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_and_subject);

        editTextClass = findViewById(R.id.editTextClass);
        editTextSubject = findViewById(R.id.editTextSubject);
        buttonAdd = findViewById(R.id.buttonAdd);

        firestore = FirebaseFirestore.getInstance();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classText = editTextClass.getText().toString().trim();
                String subjectText = editTextSubject.getText().toString().trim();

                if (!classText.isEmpty() && !subjectText.isEmpty()) {
                    addClassSubject(classText, subjectText);
                } else {
                    Toast.makeText(Add_Class_and_Subject.this, "Please enter both class and subject", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addClassSubject(String classText, String subjectText) {
        // Add the class and subject to Firestore
        firestore.collection("class_subject")
                .document()
                .set(new ClassSubject(classText, subjectText))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Add_Class_and_Subject.this, "Class and subject added successfully", Toast.LENGTH_SHORT).show();
                    editTextClass.setText("");
                    editTextSubject.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Add_Class_and_Subject.this, "Failed to add class and subject", Toast.LENGTH_SHORT).show();
                });
    }
}
