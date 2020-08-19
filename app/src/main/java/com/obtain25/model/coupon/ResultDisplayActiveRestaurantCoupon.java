package com.obtain25.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultDisplayActiveRestaurantCoupon {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result_display_active_restaurant_coupon")
    @Expose
    private ArrayList<ResultDisplayActiveRestaurantCoupon_> resultDisplayActiveRestaurantCoupon = null;

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

    public ArrayList<ResultDisplayActiveRestaurantCoupon_> getResultDisplayActiveRestaurantCoupon() {
        return resultDisplayActiveRestaurantCoupon;
    }

    public void setResultDisplayActiveRestaurantCoupon(ArrayList<ResultDisplayActiveRestaurantCoupon_> resultDisplayActiveRestaurantCoupon) {
        this.resultDisplayActiveRestaurantCoupon = resultDisplayActiveRestaurantCoupon;
    }

}

