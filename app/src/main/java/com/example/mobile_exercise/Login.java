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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button btnLogin;
    TextView toSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginPassword.getText().toString().equals("") || loginUsername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                }else{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://unilever-test.au-syd.mybluemix.net/shepherd/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonAPI jsonAPI = retrofit.create(JsonAPI.class);

                    Model model = new Model("UNILEVER",loginUsername.getText().toString(), loginPassword.getText().toString());

                    Call<Model> call = jsonAPI.getModel(model);

                    call.enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(Call<Model> call, Response<Model> response) {
                            if(!response.isSuccessful()){
                                Log.d("Code: ", "" + response.code());
                            }

                            Model models = response.body();
                            if(models.getUsername().equals(loginUsername.getText().toString()) &&
                            models.getPassword().equals(loginPassword.getText().toString())){
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
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

        toSignup = findViewById(R.id.toSignup);
        toSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });


    }
}