
package com.shrinkcom.alsaadceramicapp.pojo.SubCategory_pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("ar_sub_category_name")
    @Expose
    private String arSubCategoryName;
    @SerializedName("subcate_image")
    @Expose
    private String subcateImage;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    private final static long serialVersionUID = 4950654220803382975L;

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getArSubCategoryName() {
        return arSubCategoryName;
    }

    public void setArSubCategoryName(String arSubCategoryName) {
        this.arSubCategoryName = arSubCategoryName;
    }

    public String getSubcateImage() {
        return subcateImage;
    }

    public void setSubcateImage(String subcateImage) {
        this.subcateImage = subcateImage;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
