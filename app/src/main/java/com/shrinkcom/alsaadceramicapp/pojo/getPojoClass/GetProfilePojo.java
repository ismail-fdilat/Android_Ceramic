
package com.shrinkcom.alsaadceramicapp.pojo.getPojoClass;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetProfilePojo implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<GetProfilePojo> CREATOR = new Creator<GetProfilePojo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetProfilePojo createFromParcel(Parcel in) {
            return new GetProfilePojo(in);
        }

        public GetProfilePojo[] newArray(int size) {
            return (new GetProfilePojo[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5365775404344449955L;

    protected GetProfilePojo(Parcel in) {
        this.status = ((long) in.readValue((long.class.getClassLoader())));
        this.response = ((Response) in.readValue((Response.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    public GetProfilePojo() {
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public GetProfilePojo withStatus(long status) {
        this.status = status;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public GetProfilePojo withResponse(Response response) {
        this.response = response;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetProfilePojo withMessage(String message) {
        this.message = message;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(response);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
