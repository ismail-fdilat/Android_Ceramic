
package com.shrinkcom.alsaadceramicapp.pojo.facebookpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facebookloginpojo {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
