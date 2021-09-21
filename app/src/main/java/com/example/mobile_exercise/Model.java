package com.example.mobile_exercise;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {
    private String status;
    @SerializedName("return")
    private List<ModelArray> returns = null;
    private String message;
    private String db_identifier;

    public Model(String db_identifier) {
        this.db_identifier = db_identifier;
    }



    public String getStatus() {
        return status;
    }

    public List<ModelArray> getReturns() {
        return returns;
    }

    public String getMessage() {
        return message;
    }
}
