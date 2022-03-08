
package com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review implements Serializable, Parcelable
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
    private String otp;
    public final static Creator<Review> CREATOR = new Creator<Review>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5557650540985590731L;

    protected Review(Parcel in) {
        this.reviewId = ((String) in.readValue((String.class.getClassLoader())));
        this.review = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.fuid = ((String) in.readValue((String.class.getClassLoader())));
        this.guid = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((Object) in.readValue((Object.class.getClassLoader())));
        this.userFname = ((String) in.readValue((String.class.getClassLoader())));
        this.userLname = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.avatar = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOn = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((Object) in.readValue((Object.class.getClassLoader())));
        this.city = ((Object) in.readValue((Object.class.getClassLoader())));
        this.zipcode = ((Object) in.readValue((Object.class.getClassLoader())));
        this.token = ((Object) in.readValue((Object.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.verifiedStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.loginStatus = ((Object) in.readValue((Object.class.getClassLoader())));
        this.loginDate = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((Object) in.readValue((Object.class.getClassLoader())));
        this.gender = ((Object) in.readValue((Object.class.getClassLoader())));
        this.dob = ((Object) in.readValue((Object.class.getClassLoader())));
        this.state = ((Object) in.readValue((Object.class.getClassLoader())));
        this.loginBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.otp = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Review() {
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Review withReviewId(String reviewId) {
        this.reviewId = reviewId;
        return this;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Review withReview(String review) {
        this.review = review;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Review withRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Review withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Review withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Review withDate(String date) {
        this.date = date;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Review withId(String id) {
        this.id = id;
        return this;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public Review withFuid(String fuid) {
        this.fuid = fuid;
        return this;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Review withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Review withUsername(Object username) {
        this.username = username;
        return this;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public Review withUserFname(String userFname) {
        this.userFname = userFname;
        return this;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public Review withUserLname(String userLname) {
        this.userLname = userLname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Review withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Review withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Review withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Review withCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Review withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Review withAddress(Object address) {
        this.address = address;
        return this;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Review withCity(Object city) {
        this.city = city;
        return this;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Review withZipcode(Object zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Review withToken(Object token) {
        this.token = token;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Review withType(String type) {
        this.type = type;
        return this;
    }

    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public Review withVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
        return this;
    }

    public Object getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Review withLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
        return this;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public Review withLoginDate(String loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Review withCountry(Object country) {
        this.country = country;
        return this;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Review withGender(Object gender) {
        this.gender = gender;
        return this;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Review withDob(Object dob) {
        this.dob = dob;
        return this;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Review withState(Object state) {
        this.state = state;
        return this;
    }

    public Object getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(Object loginBy) {
        this.loginBy = loginBy;
    }

    public Review withLoginBy(Object loginBy) {
        this.loginBy = loginBy;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Review withOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(reviewId);
        dest.writeValue(review);
        dest.writeValue(rating);
        dest.writeValue(productId);
        dest.writeValue(userId);
        dest.writeValue(date);
        dest.writeValue(id);
        dest.writeValue(fuid);
        dest.writeValue(guid);
        dest.writeValue(username);
        dest.writeValue(userFname);
        dest.writeValue(userLname);
        dest.writeValue(email);
        dest.writeValue(avatar);
        dest.writeValue(password);
        dest.writeValue(createdOn);
        dest.writeValue(mobile);
        dest.writeValue(address);
        dest.writeValue(city);
        dest.writeValue(zipcode);
        dest.writeValue(token);
        dest.writeValue(type);
        dest.writeValue(verifiedStatus);
        dest.writeValue(loginStatus);
        dest.writeValue(loginDate);
        dest.writeValue(country);
        dest.writeValue(gender);
        dest.writeValue(dob);
        dest.writeValue(state);
        dest.writeValue(loginBy);
        dest.writeValue(otp);
    }

    public int describeContents() {
        return  0;
    }

}
