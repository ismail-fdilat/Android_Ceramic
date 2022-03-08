
package com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("ar_category_name")
    @Expose
    private String arCategoryName;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
     @SerializedName("subcategory count")
    @Expose
    private Integer subcatCount;


    private final static long serialVersionUID = -1087903750216260658L;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArCategoryName() {
        return arCategoryName;
    }

    public void setArCategoryName(String arCategoryName) {
        this.arCategoryName = arCategoryName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getSubcatCount() {
        return subcatCount;
    }

    public void setSubcatCount(Integer subcatCount) {
        this.subcatCount = subcatCount;
    }
}
