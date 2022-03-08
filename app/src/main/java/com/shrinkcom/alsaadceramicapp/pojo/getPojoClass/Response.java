
package com.shrinkcom.alsaadceramicapp.pojo.getPojoClass;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Response implements Serializable, Parcelable
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
    @SerializedName("baseurl")
    @Expose
    private String baseurl;
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4489215461781509029L;

    protected Response(Parcel in) {
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
        this.baseurl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Response() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response withId(String id) {
        this.id = id;
        return this;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public Response withFuid(String fuid) {
        this.fuid = fuid;
        return this;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Response withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Response withUsername(Object username) {
        this.username = username;
        return this;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public Response withUserFname(String userFname) {
        this.userFname = userFname;
        return this;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public Response withUserLname(String userLname) {
        this.userLname = userLname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Response withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Response withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Response withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Response withCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Response withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Response withAddress(Object address) {
        this.address = address;
        return this;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Response withCity(Object city) {
        this.city = city;
        return this;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Response withZipcode(Object zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Response withToken(Object token) {
        this.token = token;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Response withType(String type) {
        this.type = type;
        return this;
    }

    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public Response withVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
        return this;
    }

    public Object getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Response withLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
        return this;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public Response withLoginDate(String loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Response withCountry(Object country) {
        this.country = country;
        return this;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Response withGender(Object gender) {
        this.gender = gender;
        return this;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Response withDob(Object dob) {
        this.dob = dob;
        return this;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Response withState(Object state) {
        this.state = state;
        return this;
    }

    public Object getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(Object loginBy) {
        this.loginBy = loginBy;
    }

    public Response withLoginBy(Object loginBy) {
        this.loginBy = loginBy;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Response withOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public Response withBaseurl(String baseurl) {
        this.baseurl = baseurl;
        return this;
    }


    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeValue(baseurl);
    }

    public int describeContents() {
        return  0;
    }

}
