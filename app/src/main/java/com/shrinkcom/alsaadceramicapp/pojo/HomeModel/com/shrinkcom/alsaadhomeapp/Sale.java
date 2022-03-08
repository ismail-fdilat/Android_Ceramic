
package com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sale implements Serializable
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
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    private final static long serialVersionUID = -7254006933427611003L;

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getArDealTitle() {
        return arDealTitle;
    }

    public void setArDealTitle(String arDealTitle) {
        this.arDealTitle = arDealTitle;
    }

    public String getDealImage() {
        return dealImage;
    }

    public void setDealImage(String dealImage) {
        this.dealImage = dealImage;
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

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
