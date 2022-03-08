
package com.shrinkcom.alsaadceramicapp.pojo.products_pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("uploaded_on")
    @Expose
    private String uploadedOn;
    @SerializedName("prod_id")
    @Expose
    private String prodId;
    private final static long serialVersionUID = 5304404633576141817L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(String uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

}
