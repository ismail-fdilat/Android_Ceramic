
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Serializable, Parcelable
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
    @SerializedName("thumbnail_image")
    @Expose
    private String thumbnail_image;
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
    @SerializedName("new_quantity")
    @Expose
    private String newQuantity;
    @SerializedName("sku")
    @Expose
    private Object sku;
    @SerializedName("qty_in_ml")
    @Expose
    private String qtyInMl;
    @SerializedName("manufacture_year")
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

    @SerializedName("sale_price")
    @Expose
    private String sale_price;

    @SerializedName("created_on")
    @Expose
    private Object createdOn;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("size")
    @Expose
    private List<Size> size = new ArrayList<Size>();
    @SerializedName("brand")
    @Expose
    private Brand brand;
    public final static Creator<Product> CREATOR = new Creator<Product>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3709265126794182882L;

    protected Product(Parcel in) {
        this.prodId = ((String) in.readValue((String.class.getClassLoader())));
        this.prodTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.prodTitleAr = ((String) in.readValue((String.class.getClassLoader())));
        this.prodPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.prodShortDesc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.prodDesc = ((String) in.readValue((String.class.getClassLoader())));
        this.prodDescAr = ((String) in.readValue((String.class.getClassLoader())));
        this.prodCateId = ((String) in.readValue((String.class.getClassLoader())));
        this.prodSubcateId = ((String) in.readValue((String.class.getClassLoader())));
        this.prodImage = ((String) in.readValue((String.class.getClassLoader())));
        this.prodImage2 = ((String) in.readValue((String.class.getClassLoader())));
        this.prodImage3 = ((String) in.readValue((String.class.getClassLoader())));
        this.stock = ((String) in.readValue((String.class.getClassLoader())));
        this.brandId = ((String) in.readValue((String.class.getClassLoader())));
        this.regions = ((String) in.readValue((String.class.getClassLoader())));
        this.prodSubsubcateId = ((String) in.readValue((String.class.getClassLoader())));
        this.group = ((String) in.readValue((String.class.getClassLoader())));
        this.color = ((String) in.readValue((String.class.getClassLoader())));
        this.condition = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.newQuantity = ((String) in.readValue((String.class.getClassLoader())));
        this.sku = ((Object) in.readValue((Object.class.getClassLoader())));
        this.qtyInMl = ((String) in.readValue((String.class.getClassLoader())));
        this.manufactureYear = ((String) in.readValue((String.class.getClassLoader())));
        this.offerNote = ((String) in.readValue((String.class.getClassLoader())));
        this.offerNoteAr = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingFree = ((Object) in.readValue((Object.class.getClassLoader())));
        this.liveOnSite = ((String) in.readValue((String.class.getClassLoader())));
        this.createdOn = ((Object) in.readValue((Object.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.sale_price = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnail_image = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.images, (Image.class.getClassLoader()));
        in.readList(this.size, (Size.class.getClassLoader()));
        this.brand = ((Brand) in.readValue((Brand.class.getClassLoader())));
    }

    public Product() {
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Product withProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public Product withProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
        return this;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public String getProdTitleAr() {
        return prodTitleAr;
    }

    public void setProdTitleAr(String prodTitleAr) {
        this.prodTitleAr = prodTitleAr;
    }

    public Product withProdTitleAr(String prodTitleAr) {
        this.prodTitleAr = prodTitleAr;
        return this;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Product withProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
        return this;
    }

    public Object getProdShortDesc() {
        return prodShortDesc;
    }

    public void setProdShortDesc(Object prodShortDesc) {
        this.prodShortDesc = prodShortDesc;
    }

    public Product withProdShortDesc(Object prodShortDesc) {
        this.prodShortDesc = prodShortDesc;
        return this;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public Product withProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
        return this;
    }

    public String getProdDescAr() {
        return prodDescAr;
    }

    public void setProdDescAr(String prodDescAr) {
        this.prodDescAr = prodDescAr;
    }

    public Product withProdDescAr(String prodDescAr) {
        this.prodDescAr = prodDescAr;
        return this;
    }

    public String getProdCateId() {
        return prodCateId;
    }

    public void setProdCateId(String prodCateId) {
        this.prodCateId = prodCateId;
    }

    public Product withProdCateId(String prodCateId) {
        this.prodCateId = prodCateId;
        return this;
    }

    public String getProdSubcateId() {
        return prodSubcateId;
    }

    public void setProdSubcateId(String prodSubcateId) {
        this.prodSubcateId = prodSubcateId;
    }

    public Product withProdSubcateId(String prodSubcateId) {
        this.prodSubcateId = prodSubcateId;
        return this;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public Product withProdImage(String prodImage) {
        this.prodImage = prodImage;
        return this;
    }

    public String getProdImage2() {
        return prodImage2;
    }

    public void setProdImage2(String prodImage2) {
        this.prodImage2 = prodImage2;
    }

    public Product withProdImage2(String prodImage2) {
        this.prodImage2 = prodImage2;
        return this;
    }

    public String getProdImage3() {
        return prodImage3;
    }

    public void setProdImage3(String prodImage3) {
        this.prodImage3 = prodImage3;
    }

    public Product withProdImage3(String prodImage3) {
        this.prodImage3 = prodImage3;
        return this;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Product withStock(String stock) {
        this.stock = stock;
        return this;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Product withBrandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public Product withRegions(String regions) {
        this.regions = regions;
        return this;
    }

    public String getProdSubsubcateId() {
        return prodSubsubcateId;
    }

    public void setProdSubsubcateId(String prodSubsubcateId) {
        this.prodSubsubcateId = prodSubsubcateId;
    }

    public Product withProdSubsubcateId(String prodSubsubcateId) {
        this.prodSubsubcateId = prodSubsubcateId;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Product withGroup(String group) {
        this.group = group;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Product withColor(String color) {
        this.color = color;
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Product withCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Product withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(String newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Product withNewQuantity(String newQuantity) {
        this.newQuantity = newQuantity;
        return this;
    }

    public Object getSku() {
        return sku;
    }

    public void setSku(Object sku) {
        this.sku = sku;
    }

    public Product withSku(Object sku) {
        this.sku = sku;
        return this;
    }

    public String getQtyInMl() {
        return qtyInMl;
    }

    public void setQtyInMl(String qtyInMl) {
        this.qtyInMl = qtyInMl;
    }

    public Product withQtyInMl(String qtyInMl) {
        this.qtyInMl = qtyInMl;
        return this;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Product withManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
        return this;
    }

    public String getOfferNote() {
        return offerNote;
    }

    public void setOfferNote(String offerNote) {
        this.offerNote = offerNote;
    }

    public Product withOfferNote(String offerNote) {
        this.offerNote = offerNote;
        return this;
    }

    public String getOfferNoteAr() {
        return offerNoteAr;
    }

    public void setOfferNoteAr(String offerNoteAr) {
        this.offerNoteAr = offerNoteAr;
    }

    public Product withOfferNoteAr(String offerNoteAr) {
        this.offerNoteAr = offerNoteAr;
        return this;
    }

    public Object getShippingFree() {
        return shippingFree;
    }

    public void setShippingFree(Object shippingFree) {
        this.shippingFree = shippingFree;
    }

    public Product withShippingFree(Object shippingFree) {
        this.shippingFree = shippingFree;
        return this;
    }

    public String getLiveOnSite() {
        return liveOnSite;
    }

    public void setLiveOnSite(String liveOnSite) {
        this.liveOnSite = liveOnSite;
    }

    public Product withLiveOnSite(String liveOnSite) {
        this.liveOnSite = liveOnSite;
        return this;
    }

    public Object getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
    }

    public Product withCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product withStatus(String status) {
        this.status = status;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Product withImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public Product withSize(List<Size> size) {
        this.size = size;
        return this;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product withBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(prodId);
        dest.writeValue(prodTitle);
        dest.writeValue(prodTitleAr);
        dest.writeValue(prodPrice);
        dest.writeValue(prodShortDesc);
        dest.writeValue(prodDesc);
        dest.writeValue(prodDescAr);
        dest.writeValue(prodCateId);
        dest.writeValue(prodSubcateId);
        dest.writeValue(prodImage);
        dest.writeValue(prodImage2);
        dest.writeValue(prodImage3);
        dest.writeValue(stock);
        dest.writeValue(brandId);
        dest.writeValue(regions);
        dest.writeValue(prodSubsubcateId);
        dest.writeValue(group);
        dest.writeValue(color);
        dest.writeValue(condition);
        dest.writeValue(quantity);
        dest.writeValue(newQuantity);
        dest.writeValue(sku);
        dest.writeValue(qtyInMl);
        dest.writeValue(manufactureYear);
        dest.writeValue(offerNote);
        dest.writeValue(offerNoteAr);
        dest.writeValue(shippingFree);
        dest.writeValue(liveOnSite);
        dest.writeValue(createdOn);
        dest.writeValue(status);
        dest.writeList(images);
        dest.writeList(size);
        dest.writeValue(brand);
    }

    public int describeContents() {
        return  0;
    }

}
