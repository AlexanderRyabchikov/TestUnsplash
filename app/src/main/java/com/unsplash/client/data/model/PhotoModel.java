package com.unsplash.client.data.model;

import com.google.gson.annotations.SerializedName;

public class PhotoModel{

    @SerializedName("id")
    private String id;

    @SerializedName("description")
    private
    String description;

    @SerializedName("urls")
    private UrlsPhoto urlsPhoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UrlsPhoto getUrlsPhoto() {
        return urlsPhoto;
    }

    public void setUrlsPhoto(UrlsPhoto urlsPhoto) {
        this.urlsPhoto = urlsPhoto;
    }
}
