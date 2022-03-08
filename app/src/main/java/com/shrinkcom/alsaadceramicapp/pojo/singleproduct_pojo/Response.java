
package com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable {

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
    @SerializedName("rating")
    @Expose
    private String rating;
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
    @SerializedName("new_quantity")
    @Expose
    private String newQuantity;
    @SerializedName("average_rating")
    @Expose
    private String average_rating;
    @SerializedName("user_rating")
    @Expose
    private String user_rating;
    @SerializedName("sku")
    @Expose
    private Object sku;
    @SerializedName("qty_in_ml")
    @Expose
    private String qtyInMl;
    @SerializedName("model_number")
    @Expose
    private String manufactureYear;
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

    @SerializedName("offer_price")
    @Expose
    private String offer_price;

    @SerializedName("sale_price")
    @Expose
    private String salePrice;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;
    @SerializedName("manufacture_country")
    @Expose
    private String manufacture_country;
    @SerializedName("city")
    @Expose
    private String city;


    @SerializedName("created_on")
    @Expose
    private Object createdOn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("surface_shape")
    @Expose
    private String surface_shape;
    @SerializedName("thumbnail_image")
    @Expose
    private String thumbnail_image;
    @SerializedName("delivery_time")
    @Expose
    private String delivery_time;

    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("width")
    @Expose
    private Integer width;

    @SerializedName("thickness")
    @Expose
    private Integer thickness;

    @SerializedName("packet_area")
    @Expose
    private Double packet_area;


    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("size")
    @Expose
    private List<Size> size = null;
    @SerializedName("brand")
    @Expose
    private Brand brand;

    @SerializedName("seller")
    @Expose
    private List<Seller> seller = null;

    @SerializedName("review")
    @Expose
    private List<Review> review = null;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    private final static long serialVersionUID = -4329667467554678719L;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getManufacture_country() {
        return manufacture_country;
    }

    public void setManufacture_country(String manufacture_country) {
        this.manufacture_country = manufacture_country;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public String getSurface_shape() {
        return surface_shape;
    }

    public void setSurface_shape(String surface_shape) {
        this.surface_shape = surface_shape;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProdId() {
        return prodId;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
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

    public String getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(String newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Object getSku() {
        return sku;
    }

    public void setSku(Object sku) {
        this.sku = sku;
    }

    public String getQtyInMl() {
        return qtyInMl;
    }

    public void setQtyInMl(String qtyInMl) {
        this.qtyInMl = qtyInMl;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
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


    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public List<Seller> getSeller() {
        return seller;
    }

    public void setSeller(List<Seller> seller) {
        this.seller = seller;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getThickness() {
        return thickness;
    }

    public void setThickness(Integer thickness) {
        this.thickness = thickness;
    }

    public Double getPacket_area() {
        return packet_area;
    }

    public void setPacket_area(Double packet_area) {
        this.packet_area = packet_area;
    }
}
