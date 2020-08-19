package com.obtain25.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {


    @SerializedName("session_data")
    @Expose
    private ResultFrontLogin sessionData;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public ResultFrontLogin getSessionData() {
        return sessionData;
    }

    public void setSessionData(ResultFrontLogin sessionData) {
        this.sessionData = sessionData;
    }

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

}