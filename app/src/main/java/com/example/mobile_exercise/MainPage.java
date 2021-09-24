package com.example.mobile_exercise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends Fragment {

    RecyclerView mainRecyclerView;
    LinearLayout nothing;
    TextView textView;

    MainAdapter mainAdapter;
    HTTPHandler httpHandler;
    DatabaseHelper databaseHelper;

    ArrayList<ModelArray> modelArrayArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        databaseHelper = new DatabaseHelper(getActivity());

        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);
        nothing = view.findViewById(R.id.nothing);
        textView = view.findViewById(R.id.textview);
        getUsersList();

        return view;
    }

    public void getUsersList(){
        httpHandler = new HTTPHandler();

        Call<Model> modelCall = httpHandler.jsonAPI.showUsers("UNILEVER");
        modelCall.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(retrofit2.Call<Model> call, Response<Model> response) {
                if(!response.isSuccessful()){
                    Log.d("Code: ", response.code() + "");
                    return;
                }
                textView.setText("Loading...");
                mainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                //modelArrayArrayList;
                for(int i = 0; i < response.body().getReturns().size(); i++){
                    if(response.body().getReturns().get(i).getFirstname() != null){
                        modelArrayArrayList.add(response.body().getReturns().get(i));
                    }
                }

                if(modelArrayArrayList.size() > 0){
                    nothing.setVisibility(View.GONE);
                }else{
                    mainRecyclerView.setVisibility(View.GONE);
                }
                textView.setText("Nothing to show!");
                mainAdapter = new MainAdapter(modelArrayArrayList, getActivity());
                mainRecyclerView.setAdapter(mainAdapter);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();

                textView.setText("Loading...");
                mainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                modelArrayArrayList.addAll(databaseHelper.showUsers());

                if(modelArrayArrayList.size() > 0){
                    nothing.setVisibility(View.GONE);
                }else{
                    mainRecyclerView.setVisibility(View.GONE);
                }
                textView.setText("Nothing to show!");
                mainAdapter = new MainAdapter(modelArrayArrayList, getActivity());
                mainRecyclerView.setAdapter(mainAdapter);
            }
        });
    }
}