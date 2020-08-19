package com.obtain25.model.term;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultGetTurms {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result_GetTurms")
    @Expose
    private ResultGetTurms_ resultGetTurms;

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

    public ResultGetTurms_ getResultGetTurms() {
        return resultGetTurms;
    }

    public void setResultGetTurms(ResultGetTurms_ resultGetTurms) {
        this.resultGetTurms = resultGetTurms;
    }
}
