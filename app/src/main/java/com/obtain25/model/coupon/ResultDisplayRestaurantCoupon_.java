package com.obtain25.model.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultDisplayRestaurantCoupon_ {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("discount_value")
    @Expose
    private String discountValue;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("minimum_amount")
    @Expose
    private String minimumAmount;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("action_taken_by_admin")
    @Expose
    private Object actionTakenByAdmin;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("resto_photo")
    @Expose
    private String restoPhoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Object getActionTakenByAdmin() {
        return actionTakenByAdmin;
    }

    public void setActionTakenByAdmin(Object actionTakenByAdmin) {
        this.actionTakenByAdmin = actionTakenByAdmin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestoPhoto() {
        return restoPhoto;
    }

    public void setRestoPhoto(String restoPhoto) {
        this.restoPhoto = restoPhoto;
    }
}
