package com.gretsa.studentattendance;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class View_Users extends AppCompatActivity {

    private TextView textViewTitle;
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private List<User> userList;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        textViewTitle = findViewById(R.id.textViewTitle);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(userAdapter);

        firestore = FirebaseFirestore.getInstance();

        loadUsersFromFirebase();
    }

    private void loadUsersFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();

            Query usersQuery = firestore.collection("Users")
                    .whereNotEqualTo("userId", currentUserId);  // Exclude the current user

            usersQuery.get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            userList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                userList.add(user);
                            }
                            userAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(View_Users.this, "Failed to load users: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(View_Users.this, "No authenticated user found.", Toast.LENGTH_SHORT).show();
        }
    }
}
