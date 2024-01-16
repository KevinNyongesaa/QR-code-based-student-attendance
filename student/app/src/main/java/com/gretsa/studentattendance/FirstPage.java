package com.gretsa.studentattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {

    private Button studentButton;
    private Button adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        studentButton = findViewById(R.id.buttonStudent);
        adminButton = findViewById(R.id.buttonAdmin);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start student activity
                startActivity(new Intent(FirstPage.this, Login.class));
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start admin activity
                startActivity(new Intent(FirstPage.this, AdminLogin.class));
            }
        });
    }
}
