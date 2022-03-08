
package com.shrinkcom.alsaadceramicapp.pojo.HomeSlidermodel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable, Parcelable
{

    @SerializedName("deal_id")
    @Expose
    private String dealId;
    @SerializedName("deal_title")
    @Expose
    private String dealTitle;
    @SerializedName("ar_deal_title")
    @Expose
    private String arDealTitle;
    @SerializedName("deal_image")
    @Expose
    private String dealImage;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    public final static Creator<Response> CREATOR = new Creator<Response>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        public Response[] newArray(int size) {
            return (new Response[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7632573890418618504L;

    protected Response(Parcel in) {
        this.dealId = ((String) in.readValue((String.class.getClassLoader())));
        this.dealTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.arDealTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.dealImage = ((String) in.readValue((String.class.getClassLoader())));
        this.startDate = ((String) in.readValue((String.class.getClassLoader())));
        this.endDate = ((String) in.readValue((String.class.getClassLoader())));
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.endTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param arDealTitle
     * @param endDate
     * @param dealId
     * @param dealTitle
     * @param startTime
     * @param endTime
     * @param dealImage
     * @param startDate
     */
    public Response(String dealId, String dealTitle, String arDealTitle, String dealImage, String startDate, String endDate, String startTime, String endTime) {
        super();
        this.dealId = dealId;
        this.dealTitle = dealTitle;
        this.arDealTitle = arDealTitle;
        this.dealImage = dealImage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public Response withDealId(String dealId) {
        this.dealId = dealId;
        return this;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public Response withDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
        return this;
    }

    public String getArDealTitle() {
        return arDealTitle;
    }

    public void setArDealTitle(String arDealTitle) {
        this.arDealTitle = arDealTitle;
    }

    public Response withArDealTitle(String arDealTitle) {
        this.arDealTitle = arDealTitle;
        return this;
    }

    public String getDealImage() {
        return dealImage;
    }

    public void setDealImage(String dealImage) {
        this.dealImage = dealImage;
    }

    public Response withDealImage(String dealImage) {
        this.dealImage = dealImage;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Response withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Response withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Response withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Response withEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dealId);
        dest.writeValue(dealTitle);
        dest.writeValue(arDealTitle);
        dest.writeValue(dealImage);
        dest.writeValue(startDate);
        dest.writeValue(endDate);
        dest.writeValue(startTime);
        dest.writeValue(endTime);
    }

    public int describeContents() {
        return  0;
    }

}
