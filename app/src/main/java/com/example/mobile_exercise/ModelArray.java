package com.example.mobile_exercise;

public class ModelArray {
    private String address;
    private String contact_number;
    private String firstname;
    private String lastname;
    private String password;
    private String username;
    private String db_identifier;

    public ModelArray(String address, String contact_number, String firstname, String lastname, String password, String username, String db_identifier) {
        this.address = address;
        this.contact_number = contact_number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.username = username;
        this.db_identifier = db_identifier;
    }

    public ModelArray(String address, String contact_number, String firstname, String lastname, String username, String db_identifier) {
        this.address = address;
        this.contact_number = contact_number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.db_identifier = db_identifier;
    }

    public ModelArray(String address, String contact_number, String firstname, String lastname) {
        this.address = address;
        this.contact_number = contact_number;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getContact_number() {
        return contact_number;
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
