
package com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo;

import java.io.Serializable;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Seller implements Serializable, Parcelable
{

    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("sname")
    @Expose
    private String sname;
    @SerializedName("semail")
    @Expose
    private String semail;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_name")
    @Expose
    private String city_name;
    public final static Creator<Seller> CREATOR = new Creator<Seller>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Seller createFromParcel(android.os.Parcel in) {
            return new Seller(in);
        }

        public Seller[] newArray(int size) {
            return (new Seller[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7485511188573983592L;

    protected Seller(android.os.Parcel in) {
        this.sellerId = ((String) in.readValue((String.class.getClassLoader())));
        this.sname = ((String) in.readValue((String.class.getClassLoader())));
        this.semail = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.city_name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Seller() {
    }

    public String getCity() {
        return city;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(sellerId);
        dest.writeValue(sname);
        dest.writeValue(semail);
        dest.writeValue(phone);
    }

    public int describeContents() {
        return  0;
    }

}
