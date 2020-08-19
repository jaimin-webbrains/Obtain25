package com.obtain25.model.sendrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAcceptedOrRejected {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response_RestoDisplayUserRequestAcceptedOrRejected")
    @Expose
    private ArrayList<ResponseAcceptedOrRejected_> responseRestoDisplayUserRequestAcceptedOrRejected = null;

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

    public ArrayList<ResponseAcceptedOrRejected_> getResponseRestoDisplayUserRequestAcceptedOrRejected() {
        return responseRestoDisplayUserRequestAcceptedOrRejected;
    }

    public void setResponseRestoDisplayUserRequestAcceptedOrRejected(ArrayList<ResponseAcceptedOrRejected_> responseRestoDisplayUserRequestAcceptedOrRejected) {
        this.responseRestoDisplayUserRequestAcceptedOrRejected = responseRestoDisplayUserRequestAcceptedOrRejected;
    }
}
