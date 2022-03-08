
package com.shrinkcom.alsaadceramicapp.pojo.BrandModel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review implements Serializable
{

    @SerializedName("review_id")
    @Expose
    private String reviewId;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Fuid")
    @Expose
    private String fuid;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("user_fname")
    @Expose
    private String userFname;
    @SerializedName("user_lname")
    @Expose
    private String userLname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("zipcode")
    @Expose
    private Object zipcode;
    @SerializedName("token")
    @Expose
    private Object token;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("verified_status")
    @Expose
    private String verifiedStatus;
    @SerializedName("login_status")
    @Expose
    private Object loginStatus;
    @SerializedName("login_date")
    @Expose
    private String loginDate;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("login_by")
    @Expose
    private Object loginBy;
    @SerializedName("otp")
    @Expose
    private Object otp;
    private final static long serialVersionUID = 5725991200683277715L;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public Object getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(Object loginBy) {
        this.loginBy = loginBy;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

}
