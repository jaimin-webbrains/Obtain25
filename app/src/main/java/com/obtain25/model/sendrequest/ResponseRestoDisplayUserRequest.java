package com.obtain25.model.sendrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseRestoDisplayUserRequest {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response_RestoDisplayUserRequest")
    @Expose
    private ArrayList<ResponseRestoDisplayUserRequest_> responseRestoDisplayUserRequest = null;

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

    public ArrayList<ResponseRestoDisplayUserRequest_> getResponseRestoDisplayUserRequest() {
        return responseRestoDisplayUserRequest;
    }

    public void setResponseRestoDisplayUserRequest(ArrayList<ResponseRestoDisplayUserRequest_> responseRestoDisplayUserRequest) {
        this.responseRestoDisplayUserRequest = responseRestoDisplayUserRequest;
    }
}
