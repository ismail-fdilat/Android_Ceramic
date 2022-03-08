
package com.shrinkcom.alsaadceramicapp.pojo.products_pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productlistpojo implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("response")
    @Expose
    private List<Response> response = new ArrayList<Response>();
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -2079652811581055887L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
