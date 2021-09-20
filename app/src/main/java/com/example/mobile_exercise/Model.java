package com.example.mobile_exercise;

public class Model {
    private String address;
    private int contact_number;
    private String created_at;
    private String deleted_at;
    private String firstname;
    private String lastname;
    private String password;
    private String username;
    private String db_identifier;

    public Model(String password, String username, String db_identifier) {
        this.password = password;
        this.username = username;
        this.db_identifier = db_identifier;
    }

    public String getAddress() {
        return address;
    }

    public int getContact_number() {
        return contact_number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
