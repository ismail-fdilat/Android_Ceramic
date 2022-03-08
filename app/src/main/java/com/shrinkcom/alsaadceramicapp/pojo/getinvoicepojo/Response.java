package com.shrinkcom.alsaadceramicapp.pojo.getinvoicepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Response implements Serializable
{

    @SerializedName("invoice")
    @Expose
    private String invoice;
    private final static long serialVersionUID = 4477047638051180743L;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

}
