package com.example.mobile_exercise;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseHelper extends SQLiteOpenHelper {

    HTTPHandler httpHandler;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "unilever.db", null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(" +
                "username text primary key not null, " +
                "firstname text, " +
                "lastname text, " +
                "address text, " +
                "contact_number text)");

        sqLiteDatabase.execSQL("create table admins(" +
                "id integer primary key not null, " +
                "username text, " +
                "password text)");

        insertToDb(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists admins");
        onCreate(sqLiteDatabase);
    }

    //methods connecting db to api
    public void insertToDb(SQLiteDatabase sqLiteDatabase){
        httpHandler = new HTTPHandler();

        Call<Model> call = httpHandler.jsonAPI.showUsers("UNILEVER");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if(!response.isSuccessful()){
                    Log.d("Code: ", response.code() + "");
                    return;
                }

                for(int i = 0; i < response.body().getReturns().size(); i++){
                    if(response.body().getReturns().get(i).getFirstname() != null){
                        sqLiteDatabase.execSQL("insert into users(" +
                                "username, firstname, lastname, address, contact_number) values(" +
                                "'" + response.body().getReturns().get(i).getUsername() + "', " +
                                "'" + response.body().getReturns().get(i).getFirstname() + "', " +
                                "'" + response.body().getReturns().get(i).getLastname() + "', " +
                                "'" + response.body().getReturns().get(i).getAddress() + "', " +
                                "'" + response.body().getReturns().get(i).getContact_number() + "')");
                    }else{
                        sqLiteDatabase.execSQL("insert into admins(" +
                                "username, password) values(" +
                                "'" + response.body().getReturns().get(i).getUsername() + "', " +
                                "'" + response.body().getReturns().get(i).getPassword() + "')");
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void singleInsert(boolean isAdmin, ModelArray modelArray){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        if(isAdmin){
            sqLiteDatabase.execSQL("insert into admins(" +
                    "username, password) values(" +
                    "'"+modelArray.getUsername()+"', " +
                    "'"+modelArray.getPassword()+"')");
        }else{
            sqLiteDatabase.execSQL("insert into users(" +
                    "username, firstname, lastname, address, contact_number) values(" +
                    "'"+modelArray.getUsername()+"', " +
                    "'"+modelArray.getFirstname()+"', " +
                    "'"+modelArray.getLastname()+"', " +
                    "'"+modelArray.getAddress()+"', " +
                    "'"+modelArray.getContact_number()+"')");
        }
    }

//    public void truncateTables(){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("delete from admins");
//        sqLiteDatabase.execSQL("delete from users");
//    }

    public Cursor login(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select username, password from admins where " +
                "username = '"+username+"' and " +
                "password = '"+password+"'", null);
    }

    public ArrayList<ModelArray> showUsers(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<ModelArray> modelArrayArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select firstname, lastname, address, contact_number from users", null);

        while (cursor.moveToNext()){
            String firstname = cursor.getString(0);
            String lastname = cursor.getString(1);
            String address = cursor.getString(2);
            String contact = cursor.getString(3);

            ModelArray modelArray = new ModelArray(address, contact, firstname, lastname);
            modelArrayArrayList.add(modelArray);
        }

        return modelArrayArrayList;
    }

    public void editUsers(ModelArray modelArray){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("update users set " +
                "firstname = '"+modelArray.getFirstname()+"', " +
                "lastname = '"+modelArray.getLastname()+"', " +
                "address = '"+modelArray.getAddress()+"', " +
                "contact_number = '"+modelArray.getContact_number()+"' " +
                "where username = '"+modelArray.getUsername()+"'");
    }

    public void deleteUsers(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from users where username = '"+username+"'");
    }
}
