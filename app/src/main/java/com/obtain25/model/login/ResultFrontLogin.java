package com.obtain25.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultFrontLogin {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("adharcard")
    @Expose
    private String adharcard;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("turms_and_condition")
    @Expose
    private Object turmsAndCondition;
    @SerializedName("resto_photo")
    @Expose
    private String restoPhoto;
    @SerializedName("contact_photo")
    @Expose
    private Object contactPhoto;
    @SerializedName("bank_name")
    @Expose
    private Object bankName;
    @SerializedName("bank_branch")
    @Expose
    private Object bankBranch;
    @SerializedName("account_holder_name")
    @Expose
    private Object accountHolderName;
    @SerializedName("account_number")
    @Expose
    private Object accountNumber;
    @SerializedName("ifsc_code")
    @Expose
    private Object ifscCode;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("registered_from")
    @Expose
    private String registeredFrom;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("user_tocken")
    @Expose
    private String user_tocken;

    @SerializedName("login_time")
    @Expose
    private Integer loginTime;
    @SerializedName("food_license")
    @Expose
    private String foodLicense;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAdharcard() {
        return adharcard;
    }

    public void setAdharcard(String adharcard) {
        this.adharcard = adharcard;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getTurmsAndCondition() {
        return turmsAndCondition;
    }

    public void setTurmsAndCondition(Object turmsAndCondition) {
        this.turmsAndCondition = turmsAndCondition;
    }

    public String getRestoPhoto() {
        return restoPhoto;
    }

    public void setRestoPhoto(String restoPhoto) {
        this.restoPhoto = restoPhoto;
    }

    public Object getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(Object contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public Object getBankName() {
        return bankName;
    }

    public void setBankName(Object bankName) {
        this.bankName = bankName;
    }

    public Object getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(Object bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Object getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(Object accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Object getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Object accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Object getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(Object ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getRegisteredFrom() {
        return registeredFrom;
    }

    public void setRegisteredFrom(String registeredFrom) {
        this.registeredFrom = registeredFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getFoodLicense() {
        return foodLicense;
    }

    public void setFoodLicense(String foodLicense) {
        this.foodLicense = foodLicense;
    }

    public String getUser_tocken() {
        return user_tocken;
    }

    public void setUser_tocken(String user_tocken) {
        this.user_tocken = user_tocken;
    }
}