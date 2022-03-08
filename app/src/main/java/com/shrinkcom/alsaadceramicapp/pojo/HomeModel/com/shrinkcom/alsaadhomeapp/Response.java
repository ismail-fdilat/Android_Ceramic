
package com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

    @SerializedName("sale")
    @Expose
    private List<Sale> sale = new ArrayList<Sale>();
    @SerializedName("todays_deal")
    @Expose
    private List<TodaysDeal> todaysDeal = new ArrayList<TodaysDeal>();
    @SerializedName("featured_brands")
    @Expose
    private List<FeaturedBrand> featuredBrands = new ArrayList<FeaturedBrand>();
    @SerializedName("category")
    @Expose
    private List<Category> category = new ArrayList<Category>();
    @SerializedName("new_arrival")
    @Expose
    private List<NewArrival> newArrival = new ArrayList<NewArrival>();
    @SerializedName("random_product")
    @Expose
    private List<RandomProduct> randomProduct = new ArrayList<RandomProduct>();
    private final static long serialVersionUID = 1759913246823552450L;

    @SerializedName("banner")
    @Expose
    private List<Banner> banner = new ArrayList<Banner>();

    public List<Sale> getSale() {
        return sale;
    }

    public void setSale(List<Sale> sale) {
        this.sale = sale;
    }

    public List<TodaysDeal> getTodaysDeal() {
        return todaysDeal;
    }

    public void setTodaysDeal(List<TodaysDeal> todaysDeal) {
        this.todaysDeal = todaysDeal;
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

    public List<NewArrival> getNewArrival() {
        return newArrival;
    }

    public void setNewArrival(List<NewArrival> newArrival) {
        this.newArrival = newArrival;
    }

    public List<RandomProduct> getRandomProduct() {
        return randomProduct;
    }

    public void setRandomProduct(List<RandomProduct> randomProduct) {
        this.randomProduct = randomProduct;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }
}
