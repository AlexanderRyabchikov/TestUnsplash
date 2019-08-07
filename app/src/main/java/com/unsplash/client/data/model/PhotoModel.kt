package com.unsplash.client.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhotoModel {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("urls")
    @Expose
    var urlsPhoto: UrlsPhoto? = null
}
