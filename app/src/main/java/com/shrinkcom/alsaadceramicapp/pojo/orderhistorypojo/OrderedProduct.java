
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderedProduct implements Serializable, Parcelable
{

    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("discount_amount")
    @Expose
    private Object discountAmount;
    @SerializedName("total_amount")
    @Expose
    private Object totalAmount;
    @SerializedName("shop")
    @Expose
    private String shop;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("location")
    @Expose
    private String location;


    @SerializedName("shop_name")
    @Expose
    private String shop_name;


    @SerializedName("city_name")
    @Expose
    private String city_name;


    @SerializedName("shopkeeper_id")
    @Expose
    private Object shopkeeperId;
    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<Product>();
    public final static Creator<OrderedProduct> CREATOR = new Creator<OrderedProduct>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OrderedProduct createFromParcel(Parcel in) {
            return new OrderedProduct(in);
        }

        public OrderedProduct[] newArray(int size) {
            return (new OrderedProduct[size]);
        }

    }
    ;
    private final static long serialVersionUID = 7125155330938603704L;

    protected OrderedProduct(Parcel in) {
        this.orderid = ((String) in.readValue((String.class.getClassLoader())));
        this.bookingId = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.totalPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.discountAmount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.totalAmount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.shop = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.shop_name = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.city_name = ((String) in.readValue((String.class.getClassLoader())));
        this.shopkeeperId = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.products, (Product.class.getClassLoader()));
    }

    public OrderedProduct() {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public OrderedProduct withOrderid(String orderid) {
        this.orderid = orderid;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public OrderedProduct withBookingId(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public OrderedProduct withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public OrderedProduct withQty(String qty) {
        this.qty = qty;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public OrderedProduct withPrice(String price) {
        this.price = price;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderedProduct withTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Object getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Object discountAmount) {
        this.discountAmount = discountAmount;
    }

    public OrderedProduct withDiscountAmount(Object discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public Object getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Object totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderedProduct withTotalAmount(Object totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public OrderedProduct withShop(String shop) {
        this.shop = shop;
        return this;
    }

    public Object getShopkeeperId() {
        return shopkeeperId;
    }

    public void setShopkeeperId(Object shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
    }

    public OrderedProduct withShopkeeperId(Object shopkeeperId) {
        this.shopkeeperId = shopkeeperId;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderedProduct withProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(orderid);
        dest.writeValue(bookingId);
        dest.writeValue(productId);
        dest.writeValue(qty);
        dest.writeValue(price);
        dest.writeValue(totalPrice);
        dest.writeValue(discountAmount);
        dest.writeValue(totalAmount);
        dest.writeValue(shop);
        dest.writeValue(shopkeeperId);
        dest.writeList(products);
    }

    public int describeContents() {
        return  0;
    }

}
