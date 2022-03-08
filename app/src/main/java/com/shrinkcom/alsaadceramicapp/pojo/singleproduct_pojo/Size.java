
package com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Size implements Serializable
{

    @SerializedName("size_id")
    @Expose
    private String sizeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ar_name")
    @Expose
    private String arName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    @SerializedName("remainqty")
    @Expose
    private String remainqty;


    @SerializedName("prod_id")
    @Expose
    private String prodId;
    private final static long serialVersionUID = 8894853970514543366L;

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }


}
