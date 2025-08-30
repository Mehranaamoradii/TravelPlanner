package com.example.Travel.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Travel.Database.VacationDatabaseBuilder;
import com.example.Travel.R;
import com.example.Travel.DAO.UserDAO;
import com.example.Travel.entities.User;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonSignup = findViewById(R.id.buttonSignup);

        userDAO = VacationDatabaseBuilder.getDatabase(getApplicationContext()).userDAO();

        buttonSignup.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(SignupActivity.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if username already exists
            new Thread(() -> {
                User existingUser = userDAO.getUserByUsername(username);
                runOnUiThread(() -> {
                    if (existingUser != null) {
                        Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Create new user
                        User newUser = new User(username, password);
                        new Thread(() -> {
                            userDAO.insert(newUser);
                            runOnUiThread(() -> {
                                Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                // Navigate to LoginActivity or MainActivity
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        }).start();
                    }
                });
            }).start();
        });
    }
}