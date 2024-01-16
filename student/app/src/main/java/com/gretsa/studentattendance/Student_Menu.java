package com.gretsa.studentattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

    public class Student_Menu extends AppCompatActivity implements View.OnClickListener {
        private CardView cardView5, cardView6, cardView1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_student_menu);

            // Initialize views
            cardView5 = findViewById(R.id.cardView5);
            cardView6 = findViewById(R.id.cardView6);
            cardView1 = findViewById(R.id.cardView1);

            // Set click listeners
            cardView5.setOnClickListener(this);
            cardView6.setOnClickListener(this);
            cardView1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cardView5:
                    viewClassAndCourse();
                    break;
                case R.id.cardView6:
                    viewAttendance();
                    break;
                case R.id.cardView1:
                    markAttendance();
                    break;
            }
        }

        private void viewClassAndCourse() {
            startActivity(new Intent(Student_Menu.this, ViewClass_Subject.class));
        }

        private void viewAttendance() {

            startActivity(new Intent(Student_Menu.this, View_Attendance.class));
        }

        private void markAttendance() {

            startActivity(new Intent(Student_Menu.this, Mark_Attendance.class));
        }
    }
