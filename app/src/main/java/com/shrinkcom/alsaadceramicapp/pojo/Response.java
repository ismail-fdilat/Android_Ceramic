
package com.shrinkcom.alsaadceramicapp.pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

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
    private String city;
    @SerializedName("city_name")
    @Expose
    private String city_name;
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
    private final static long serialVersionUID = -3825420421524665149L;

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

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
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
