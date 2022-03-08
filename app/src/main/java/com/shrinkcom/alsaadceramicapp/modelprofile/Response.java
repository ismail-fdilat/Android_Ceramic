package com.shrinkcom.alsaadceramicapp.modelprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("baseurl")
    @Expose
    private String baseurl;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

}