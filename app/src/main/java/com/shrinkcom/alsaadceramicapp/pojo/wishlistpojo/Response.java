
package com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable
{

    @SerializedName("wish_id")
    @Expose
    private String wishId;
    @SerializedName("wish_product_id")
    @Expose
    private String wishProductId;
    @SerializedName("wish_user_id")
    @Expose
    private String wishUserId;
    @SerializedName("wish_user_ip")
    @Expose
    private Object wishUserIp;
    @SerializedName("wish_date")
    @Expose
    private String wishDate;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    private final static long serialVersionUID = 8820507901827410001L;

    public String getWishId() {
        return wishId;
    }

    public void setWishId(String wishId) {
        this.wishId = wishId;
    }

    public String getWishProductId() {
        return wishProductId;
    }

    public void setWishProductId(String wishProductId) {
        this.wishProductId = wishProductId;
    }

    public String getWishUserId() {
        return wishUserId;
    }

    public void setWishUserId(String wishUserId) {
        this.wishUserId = wishUserId;
    }

    public Object getWishUserIp() {
        return wishUserIp;
    }

    public void setWishUserIp(Object wishUserIp) {
        this.wishUserIp = wishUserIp;
    }

    public String getWishDate() {
        return wishDate;
    }

    public void setWishDate(String wishDate) {
        this.wishDate = wishDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
