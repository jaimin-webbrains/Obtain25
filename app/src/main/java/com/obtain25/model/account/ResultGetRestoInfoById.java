package com.obtain25.model.account;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResultGetRestoInfoById {
    @SerializedName("result_GetRestoInfoById")
    @Expose
    private ResultGetRestoInfoById_ resultGetRestoInfoById;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public ResultGetRestoInfoById_ getResultGetRestoInfoById() {
        return resultGetRestoInfoById;
    }

    public void setResultGetRestoInfoById(ResultGetRestoInfoById_ resultGetRestoInfoById) {
        this.resultGetRestoInfoById = resultGetRestoInfoById;
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
