package com.example.mobile_exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    HTTPHandler httpHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpHandler = new HTTPHandler();

        showUsers();
    }

    int counter = 0;
    public void showUsers(){
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
                if (counter == 1){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new MainPage());
                    fragmentTransaction.commit();
                }else{
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new NothingToShow());
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}