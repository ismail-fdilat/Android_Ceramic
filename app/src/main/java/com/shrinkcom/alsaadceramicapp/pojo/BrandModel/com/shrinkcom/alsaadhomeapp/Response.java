
package com.shrinkcom.alsaadceramicapp.pojo.BrandModel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

    @SerializedName("prod_id")
    @Expose
    private String prodId;
    @SerializedName("prod_title")
    @Expose
    private String prodTitle;
    @SerializedName("prod_title_ar")
    @Expose
    private String prodTitleAr;
    @SerializedName("prod_price")
    @Expose
    private String prodPrice;
    @SerializedName("prod_short_desc")
    @Expose
    private Object prodShortDesc;
    @SerializedName("prod_desc")
    @Expose
    private String prodDesc;
    @SerializedName("prod_desc_ar")
    @Expose
    private String prodDescAr;
    @SerializedName("prod_cate_id")
    @Expose
    private String prodCateId;
    @SerializedName("prod_subcate_id")
    @Expose
    private String prodSubcateId;
    @SerializedName("prod_image")
    @Expose
    private String prodImage;
    @SerializedName("prod_image2")
    @Expose
    private String prodImage2;
    @SerializedName("prod_image3")
    @Expose
    private String prodImage3;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("regions")
    @Expose
    private String regions;
    @SerializedName("prod_subsubcate_id")
    @Expose
    private String prodSubsubcateId;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("new_quantity")
    @Expose
    private String newQuantity;
    @SerializedName("model_number")
    @Expose
    private String modelNumber;
    @SerializedName("offer_note")
    @Expose
    private String offerNote;
    @SerializedName("offer_note_ar")
    @Expose
    private String offerNoteAr;
    @SerializedName("shipping_free")
    @Expose
    private Object shippingFree;
    @SerializedName("live_on_site")
    @Expose
    private String liveOnSite;
    @SerializedName("created_on")
    @Expose
    private Object createdOn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("deal_id")
    @Expose
    private String dealId;
    @SerializedName("deals_active_status")
    @Expose
    private String dealsActiveStatus;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("size")
    @Expose
    private List<Size> size = new ArrayList<Size>();
    @SerializedName("brand")
    @Expose
    private Brand brand;
    @SerializedName("review")
    @Expose
    private List<Review> review = new ArrayList<Review>();
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    private final static long serialVersionUID = 6632576289788667982L;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public String getProdTitleAr() {
        return prodTitleAr;
    }

    public void setProdTitleAr(String prodTitleAr) {
        this.prodTitleAr = prodTitleAr;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Object getProdShortDesc() {
        return prodShortDesc;
    }

    public void setProdShortDesc(Object prodShortDesc) {
        this.prodShortDesc = prodShortDesc;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdDescAr() {
        return prodDescAr;
    }

    public void setProdDescAr(String prodDescAr) {
        this.prodDescAr = prodDescAr;
    }

    public String getProdCateId() {
        return prodCateId;
    }

    public void setProdCateId(String prodCateId) {
        this.prodCateId = prodCateId;
    }

    public String getProdSubcateId() {
        return prodSubcateId;
    }

    public void setProdSubcateId(String prodSubcateId) {
        this.prodSubcateId = prodSubcateId;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public String getProdImage2() {
        return prodImage2;
    }

    public void setProdImage2(String prodImage2) {
        this.prodImage2 = prodImage2;
    }

    public String getProdImage3() {
        return prodImage3;
    }

    public void setProdImage3(String prodImage3) {
        this.prodImage3 = prodImage3;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getProdSubsubcateId() {
        return prodSubsubcateId;
    }

    public void setProdSubsubcateId(String prodSubsubcateId) {
        this.prodSubsubcateId = prodSubsubcateId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(String newQuantity) {
        this.newQuantity = newQuantity;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getOfferNote() {
        return offerNote;
    }

    public void setOfferNote(String offerNote) {
        this.offerNote = offerNote;
    }

    public String getOfferNoteAr() {
        return offerNoteAr;
    }

    public void setOfferNoteAr(String offerNoteAr) {
        this.offerNoteAr = offerNoteAr;
    }

    public Object getShippingFree() {
        return shippingFree;
    }

    public void setShippingFree(Object shippingFree) {
        this.shippingFree = shippingFree;
    }

    public String getLiveOnSite() {
        return liveOnSite;
    }

    public void setLiveOnSite(String liveOnSite) {
        this.liveOnSite = liveOnSite;
    }

    public Object getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDealsActiveStatus() {
        return dealsActiveStatus;
    }

    public void setDealsActiveStatus(String dealsActiveStatus) {
        this.dealsActiveStatus = dealsActiveStatus;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
