package com.example.mobile_exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface JsonAPI {
    @POST("packuserslogin")
    Call<Model> login(
            @Query("db_identifier") String db_identifier,
            @Query("username") String username,
            @Query("password") String password);

    @POST("packusers")
    Call<Model> signup(
            @Body ModelArray modelArray);

    @GET("packusers")
    Call<Model> showUsers(@Query("db_identifier") String db_identifier);

    @PUT("packusers")
    Call<Model> editUsers(@Body ModelArray modelArray);

    @DELETE("packusers")
    Call<Model> deleteUsers(
            @Query("db_identifier") String db_identifier,
            @Query("username") String username
    );
}
