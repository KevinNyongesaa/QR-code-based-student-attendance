package com.gretsa.studentattendance;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Menu extends AppCompatActivity implements View.OnClickListener {

    private ImageView addSubjectImageView;
    private ImageView viewUsersImageView;
    private ImageView addUserImageView;
    private ImageView updateUserImageView;
    private ImageView addClassImageView;
    private ImageView viewAttendanceImageView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        addSubjectImageView = findViewById(R.id.imageView1);
        viewUsersImageView = findViewById(R.id.imageView2);
        addUserImageView = findViewById(R.id.imageView3);
        updateUserImageView = findViewById(R.id.imageView4);
        viewAttendanceImageView = findViewById(R.id.imageView6);

        addSubjectImageView.setOnClickListener(this);
        viewUsersImageView.setOnClickListener(this);
        addUserImageView.setOnClickListener(this);
        updateUserImageView.setOnClickListener(this);
        viewAttendanceImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User not logged in, handle accordingly (e.g., show login screen)
            startActivity(new Intent(Admin_Menu.this, Login.class));

            return;
        }

        switch (v.getId()) {
            case R.id.imageView1:
                startActivity(new Intent(Admin_Menu.this, Add_Class_and_Subject.class));
                break;
            case R.id.imageView2:
                startActivity(new Intent(Admin_Menu.this, View_Users.class));
                break;
            case R.id.imageView3:
                startActivity(new Intent(Admin_Menu.this, CreateQRcode.class));
                break;
            case R.id.imageView4:
                startActivity(new Intent(Admin_Menu.this, AddUser.class));
                break;

            case R.id.imageView6:
                startActivity(new Intent(Admin_Menu.this, View_Attendance.class));
                break;
        }
    }
}
