package com.obtain25.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultDisplayRestaurantCoupon {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result_display_restaurant_coupon")
    @Expose
    private ArrayList<ResultDisplayRestaurantCoupon_> resultDisplayRestaurantCoupon = null;

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

    public ArrayList<ResultDisplayRestaurantCoupon_> getResultDisplayRestaurantCoupon() {
        return resultDisplayRestaurantCoupon;
    }

    public void setResultDisplayRestaurantCoupon(ArrayList<ResultDisplayRestaurantCoupon_> resultDisplayRestaurantCoupon) {
        this.resultDisplayRestaurantCoupon = resultDisplayRestaurantCoupon;
    }

}
