package com.example.mobile_exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;

    HTTPHandler httpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUser.class);
                startActivity(intent);
            }
        });

        httpHandler = new HTTPHandler();

        checkUsers();
    }

    int counter = 0;
    public void checkUsers(){
        Call<Model> modelCall = httpHandler.jsonAPI.showUsers("UNILEVER");
        modelCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(!response.isSuccessful()){
                    Log.d("Code: ", response.code() + "");
                    return;
                }
                //Toast.makeText(getApplicationContext(), response.body().getReturns().size() + "", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.body().getReturns().size(); i++){
                    if(response.body().getReturns().get(i).getFirstname() != null){
                        counter = 1;
                        break;
                    }
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MainPage());
                fragmentTransaction.commit();
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUsers();
    }
}