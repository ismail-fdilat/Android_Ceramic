
package com.shrinkcom.alsaadceramicapp.pojo.BrandModel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand implements Serializable
{

    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("brand_title")
    @Expose
    private String brandTitle;
    @SerializedName("ar_brand_title")
    @Expose
    private String arBrandTitle;
    @SerializedName("brand_image")
    @Expose
    private String brandImage;
    private final static long serialVersionUID = 5522890735445573942L;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandTitle() {
        return brandTitle;
    }

    public void setBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
    }

    public String getArBrandTitle() {
        return arBrandTitle;
    }

    public void setArBrandTitle(String arBrandTitle) {
        this.arBrandTitle = arBrandTitle;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

}
