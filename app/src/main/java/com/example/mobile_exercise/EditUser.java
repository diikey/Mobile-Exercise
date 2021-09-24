package com.example.mobile_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUser extends AppCompatActivity {

    EditText editFirstname, editLastname, editAddress, editContact;
    Button btnEdit;

    HTTPHandler httpHandler;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editFirstname = findViewById(R.id.editFirstname);
        editLastname = findViewById(R.id.editLastname);
        editAddress = findViewById(R.id.editAddress);
        editContact = findViewById(R.id.editContact);

        getUser();

        httpHandler = new HTTPHandler();
        databaseHelper = new DatabaseHelper(getApplicationContext());

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editFirstname.getText().toString().equals("") ||
                        editLastname.getText().toString().equals("") ||
                        editAddress.getText().toString().equals("") ||
                        editContact.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill up all fields", Toast.LENGTH_SHORT).show();
                }else{
                    ModelArray modelArray = new ModelArray(editAddress.getText().toString(),
                            editContact.getText().toString(),
                            editFirstname.getText().toString(),
                            editLastname.getText().toString(),
                            username,
                            "UNILEVER");

                    Call<Model> modelCall = httpHandler.jsonAPI.editUsers(modelArray);
                    modelCall.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            if(!response.isSuccessful()){
                                Log.d("Code: ", response.code() + "");
                                return;
                            }
                            databaseHelper.editUsers(modelArray);
                            Toast.makeText(getApplicationContext(), "Edited Successfully", Toast.LENGTH_SHORT).show();
                            finish();
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
    }

    String username;
    public void getUser(){
        Bundle bundle = getIntent().getExtras();
        editFirstname.setText(bundle.getString("firstname"));
        editLastname.setText(bundle.getString("lastname"));
        editAddress.setText(bundle.getString("address"));
        editContact.setText(bundle.getString("contact"));
        username = bundle.getString("username");
    }
}