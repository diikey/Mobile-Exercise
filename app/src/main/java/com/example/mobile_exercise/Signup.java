package com.example.mobile_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    EditText signupUsername, signupPassword;
    Button btnSignup;
    TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);

        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signupPassword.getText().toString().equals("") || signupUsername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill up all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toLogin = findViewById(R.id.toLogin);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}