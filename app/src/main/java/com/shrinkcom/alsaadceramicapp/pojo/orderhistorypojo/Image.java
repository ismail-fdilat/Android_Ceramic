
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("uploaded_on")
    @Expose
    private String uploadedOn;
    @SerializedName("prod_id")
    @Expose
    private String prodId;
    public final static Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2248004277284445337L;

    protected Image(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.uploadedOn = ((String) in.readValue((String.class.getClassLoader())));
        this.prodId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image withId(String id) {
        this.id = id;
        return this;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Image withImage(String image) {
        this.image = image;
        return this;
    }

    public String getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(String uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public Image withUploadedOn(String uploadedOn) {
        this.uploadedOn = uploadedOn;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Image withProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(image);
        dest.writeValue(uploadedOn);
        dest.writeValue(prodId);
    }

    public int describeContents() {
        return  0;
    }

}
