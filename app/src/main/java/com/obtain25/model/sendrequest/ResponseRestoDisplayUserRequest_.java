package com.obtain25.model.sendrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRestoDisplayUserRequest_ {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("num_member")
    @Expose
    private String numMember;
    @SerializedName("date_and_time")
    @Expose
    private String dateAndTime;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumMember() {
        return numMember;
    }

    public void setNumMember(String numMember) {
        this.numMember = numMember;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
