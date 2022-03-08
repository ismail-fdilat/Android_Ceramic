
package com.shrinkcom.alsaadceramicapp.pojo.homepojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;
    @SerializedName("featured_brands")
    @Expose
    private List<FeaturedBrand> featuredBrands = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    private final static long serialVersionUID = -5217287574033527857L;

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public List<FeaturedBrand> getFeaturedBrands() {
        return featuredBrands;
    }

    public void setFeaturedBrands(List<FeaturedBrand> featuredBrands) {
        this.featuredBrands = featuredBrands;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
