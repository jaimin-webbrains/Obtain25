package com.obtain25.model.uploadphoto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultDisplayRestoPhoto {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result_DisplayRestoPhoto")
    @Expose
    private ArrayList<ResultDisplayRestoPhoto_> resultDisplayRestoPhoto = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ResultDisplayRestoPhoto_> getResultDisplayRestoPhoto() {
        return resultDisplayRestoPhoto;
    }

    public void setResultDisplayRestoPhoto(ArrayList<ResultDisplayRestoPhoto_> resultDisplayRestoPhoto) {
        this.resultDisplayRestoPhoto = resultDisplayRestoPhoto;
    }
}
