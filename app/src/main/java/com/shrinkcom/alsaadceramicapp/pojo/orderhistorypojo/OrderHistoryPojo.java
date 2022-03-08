
package com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryPojo implements Serializable, Parcelable
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
    public final static Creator<OrderHistoryPojo> CREATOR = new Creator<OrderHistoryPojo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OrderHistoryPojo createFromParcel(Parcel in) {
            return new OrderHistoryPojo(in);
        }

        public OrderHistoryPojo[] newArray(int size) {
            return (new OrderHistoryPojo[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4294885050291206651L;

    protected OrderHistoryPojo(Parcel in) {
        this.status = ((long) in.readValue((long.class.getClassLoader())));
        in.readList(this.response, (Response.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public OrderHistoryPojo() {
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public OrderHistoryPojo withStatus(long status) {
        this.status = status;
        return this;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public OrderHistoryPojo withResponse(List<Response> response) {
        this.response = response;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderHistoryPojo withMessage(String message) {
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
