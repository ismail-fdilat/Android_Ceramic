
package com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banner implements Serializable
{

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("base_url")
    @Expose
    private String base_url;


    @SerializedName("deal_id")
    @Expose
    private String deal_id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }
}
