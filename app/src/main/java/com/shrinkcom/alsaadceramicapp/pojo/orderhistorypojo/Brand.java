
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand implements Serializable, Parcelable
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
    public final static Creator<Brand> CREATOR = new Creator<Brand>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        public Brand[] newArray(int size) {
            return (new Brand[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1016482294515999750L;

    protected Brand(Parcel in) {
        this.brandId = ((String) in.readValue((String.class.getClassLoader())));
        this.brandTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.arBrandTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.brandImage = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.subCategoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.subSubCategoryId = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Brand() {
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Brand withBrandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getBrandTitle() {
        return brandTitle;
    }

    public void setBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
    }

    public Brand withBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
        return this;
    }

    public String getArBrandTitle() {
        return arBrandTitle;
    }

    public void setArBrandTitle(String arBrandTitle) {
        this.arBrandTitle = arBrandTitle;
    }

    public Brand withArBrandTitle(String arBrandTitle) {
        this.arBrandTitle = arBrandTitle;
        return this;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

    public Brand withBrandImage(String brandImage) {
        this.brandImage = brandImage;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Brand withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Brand withSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
        return this;
    }

    public Object getSubSubCategoryId() {
        return subSubCategoryId;
    }

    public void setSubSubCategoryId(Object subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
    }

    public Brand withSubSubCategoryId(Object subSubCategoryId) {
        this.subSubCategoryId = subSubCategoryId;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(brandId);
        dest.writeValue(brandTitle);
        dest.writeValue(arBrandTitle);
        dest.writeValue(brandImage);
        dest.writeValue(categoryId);
        dest.writeValue(subCategoryId);
        dest.writeValue(subSubCategoryId);
    }

    public int describeContents() {
        return  0;
    }

}
