package com.example.mobile_exercise;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HTTPHandler {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://unilever-test.au-syd.mybluemix.net/shepherd/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonAPI jsonAPI = retrofit.create(JsonAPI.class);
}
