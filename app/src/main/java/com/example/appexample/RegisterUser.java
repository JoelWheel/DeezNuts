package com.example.appexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.*;

public class RegisterUser extends AppCompatActivity {

    TextInputEditText textInputEditTextFullName, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail, textInputEditTextflat;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        textInputEditTextFullName = findViewById(R.id.fullName);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextEmail = findViewById(R.id.emailAddress);
        textInputEditTextflat = findViewById(R.id.flat);
        buttonSignUp = findViewById(R.id.createAccount);
        textViewLogin = findViewById(R.id.appName);
        progressBar = findViewById(R.id.progressBar);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                registerUser();
                progressBar.setVisibility(View.GONE);
                finish();
            }

            public void registerUser() {
                String fullName = String.valueOf(textInputEditTextFullName.getText());
                String username = String.valueOf(textInputEditTextUsername.getText());
                String password = String.valueOf(textInputEditTextPassword.getText());
                String email = String.valueOf(textInputEditTextEmail.getText());
                String flat = String.valueOf(textInputEditTextflat.getText());
                if (!fullName.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !flat.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    user = addUserToDatabase(fullName, username, password, email, flat);
                    final Statement stmt = null;
                    final Connection conn = null;
                }
            }

            User user;

            private User addUserToDatabase(String fullName, String username, String password, String email, String flat) {
                User user = null;
                final String DB_URL = "jdbc:mysql://localhost/loginregister";
                final String USERNAME = "root";
                final String PASSWORD = "";

                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    // Connected to database successfully...

                    Statement stmt = conn.createStatement();
                    String sql = "INSERT INTO users (fullName, username, password, email, flat) " +
                            "VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, fullName);
                    preparedStatement.setString(2, username);
                    preparedStatement.setString(3, password);
                    preparedStatement.setString(4, email);
                    preparedStatement.setString(5, flat);

                    //Insert row into the table
                    int addedRows = preparedStatement.executeUpdate();
                    if (addedRows > 0) {
                        user = new User();
                        user.fullName = fullName;
                        user.username = username;
                        user.password = password;
                        user.email = email;
                        user.flat = flat;
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return user;
            }
        });
    }
}



