package com.example.mobile_exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonAPI {
    @POST("http://unilever-test.au-syd.mybluemix.net/shepherd/packuserslogin")
    Call<Model> getModel(
            @Body Model model);
}
