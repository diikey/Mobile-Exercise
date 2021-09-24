package com.example.mobile_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    EditText signupUsername, signupPassword;
    Button btnSignup;
    TextView toLogin;

    HTTPHandler httpHandler;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        httpHandler = new HTTPHandler();
        //insert from api to local db
        databaseHelper = new DatabaseHelper(getApplicationContext());

        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);

        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signupPassword.getText().toString().equals("") || signupUsername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill up all fields!", Toast.LENGTH_SHORT).show();
                }else{
                    ModelArray model = new ModelArray("None","None","None","None", signupPassword.getText().toString(), signupUsername.getText().toString(), "UNILEVER");

                    Call<Model> modelArrayCall = httpHandler.jsonAPI.signup(model);

                    modelArrayCall.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            if(!response.isSuccessful()){
                                Log.d("Code: ", "" + response.code());
                                return;
                            }
                            Log.d("Code: ", "" + response.code());
                            Toast.makeText(getApplicationContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            if(!response.body().getMessage().contains("exists")){
                                databaseHelper.singleInsert(true, model);
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
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