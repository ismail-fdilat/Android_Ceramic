
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Size implements Serializable, Parcelable
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
    @SerializedName("prod_id")
    @Expose
    private String prodId;
    public final static Creator<Size> CREATOR = new Creator<Size>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        public Size[] newArray(int size) {
            return (new Size[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6477360929493319003L;

    protected Size(Parcel in) {
        this.sizeId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.arName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.size = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.prodId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Size() {
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public Size withSizeId(String sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size withName(String name) {
        this.name = name;
        return this;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public Size withArName(String arName) {
        this.arName = arName;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Size withId(String id) {
        this.id = id;
        return this;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Size withSize(String size) {
        this.size = size;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Size withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Size withProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sizeId);
        dest.writeValue(name);
        dest.writeValue(arName);
        dest.writeValue(id);
        dest.writeValue(size);
        dest.writeValue(quantity);
        dest.writeValue(prodId);
    }

    public int describeContents() {
        return  0;
    }

}
