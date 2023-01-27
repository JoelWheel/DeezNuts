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
                String fullName, username, password, email, flat;
                fullName = String.valueOf(textInputEditTextFullName.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                flat = String.valueOf(textInputEditTextflat.getText());

                final Statement stmt = null;
                final Connection conn = null;

                if(!fullName.equals("") && !username.equals("") && !password.equals("") && !email.equals("") && !flat.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String[] field = new String[5];
                                field[0] = "username";
                                field[1] = "fullName";
                                field[2] = "flat";
                                field[3] = "email";
                                field[4] = "password";
                                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/loginregister", "root", "");
                                System.out.println("Connection is created successfully:");
                                stmt = conn.createStatement();
                                String sql = "INSERT INTO users" + "VALUES (username, fullName, flat, email, password)";
                                stmt.executeUpdate(sql);
                            } catch (Exception e) {
                                System.out.println("Database connection failed!");
                            }
                            progressBar.setVisibility(View.GONE);

                        }

                    });

                }
            }
        });
    }
}