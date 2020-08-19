package com.obtain25.model.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Errors {

    @SerializedName("email")
    @Expose
    private ArrayList<String> email = null;
    @SerializedName("contact_number")
    @Expose
    private ArrayList<String> contactNumber = null;

    public ArrayList<String> getEmail() {

        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public ArrayList<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(ArrayList<String> contactNumber) {
        this.contactNumber = contactNumber;
    }
}