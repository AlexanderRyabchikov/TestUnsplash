package com.unsplash.client.data.model;

import com.google.gson.annotations.SerializedName;

public class UrlsPhoto {

    @SerializedName("regular")
    private String regular;

    @SerializedName("small")
    private String small;

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
