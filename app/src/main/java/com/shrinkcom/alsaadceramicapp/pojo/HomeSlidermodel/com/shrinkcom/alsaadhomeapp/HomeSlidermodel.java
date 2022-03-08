
package com.shrinkcom.alsaadceramicapp.pojo.HomeSlidermodel.com.shrinkcom.alsaadhomeapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeSlidermodel implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("response")
    @Expose
    private List<Response> response = new ArrayList<Response>();
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<HomeSlidermodel> CREATOR = new Creator<HomeSlidermodel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public HomeSlidermodel createFromParcel(Parcel in) {
            return new HomeSlidermodel(in);
        }

        public HomeSlidermodel[] newArray(int size) {
            return (new HomeSlidermodel[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8774350351794686379L;

    protected HomeSlidermodel(Parcel in) {
        this.status = ((long) in.readValue((long.class.getClassLoader())));
        in.readList(this.response, (Response.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public HomeSlidermodel() {
    }

    /**
     * 
     * @param response
     * @param message
     * @param status
     */
    public HomeSlidermodel(long status, List<Response> response, String message) {
        super();
        this.status = status;
        this.response = response;
        this.message = message;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public HomeSlidermodel withStatus(long status) {
        this.status = status;
        return this;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public HomeSlidermodel withResponse(List<Response> response) {
        this.response = response;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HomeSlidermodel withMessage(String message) {
        this.message = message;
        return this;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeList(response);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
