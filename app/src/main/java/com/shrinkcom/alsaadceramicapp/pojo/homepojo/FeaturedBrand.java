
package com.shrinkcom.alsaadceramicapp.pojo.homepojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedBrand implements Serializable
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
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("sub_sub_category_id")
    @Expose
    private Object subSubCategoryId;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    private final static long serialVersionUID = 6417915148929325115L;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Object getSubSubCategoryId() {
        return subSubCategoryId;
    }

    public void setSubSubCategoryId(Object subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
