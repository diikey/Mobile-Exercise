package com.example.mobile_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

    EditText addFirstname, addLastname, addAddress, addContact;
    Button btnAdd;

    HTTPHandler httpHandler;

    ModelArray modelArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        addFirstname = findViewById(R.id.addFirstname);
        addLastname = findViewById(R.id.addLastname);
        addAddress = findViewById(R.id.addAddress);
        addContact = findViewById(R.id.addContact);
        btnAdd = findViewById(R.id.btnAdd);

        httpHandler = new HTTPHandler();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addFirstname.getText().toString().equals("") ||
                        addLastname.getText().toString().equals("") ||
                        addAddress.getText().toString().equals("") ||
                        addContact.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill up all fields", Toast.LENGTH_SHORT).show();
                }else{
                    Random random = new Random();
                    modelArray = new ModelArray(addAddress.getText().toString()
                            ,addContact.getText().toString()
                            ,addFirstname.getText().toString()
                            ,addLastname.getText().toString()
                            ,addLastname.getText().toString() //password
                            ,addLastname.getText().toString() + "" + random.nextInt(100)
                            ,"UNILEVER");

                    Call<Model> modelArrayCall = httpHandler.jsonAPI.signup(modelArray);

                    modelArrayCall.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            if(!response.isSuccessful()){
                                Log.d("Code: ", "" + response.code());
                                return;
                            }
                            if(!response.body().getMessage().contains("exists")){
                                Toast.makeText(getApplicationContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();

                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Model> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}