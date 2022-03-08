
package com.shrinkcom.alsaadceramicapp.pojo.homepojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("ar_title")
    @Expose
    private Object arTitle;
    @SerializedName("ar_content")
    @Expose
    private Object arContent;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    private final static long serialVersionUID = -6815895559305553105L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getArTitle() {
        return arTitle;
    }

    public void setArTitle(Object arTitle) {
        this.arTitle = arTitle;
    }

    public Object getArContent() {
        return arContent;
    }

    public void setArContent(Object arContent) {
        this.arContent = arContent;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
