package com.unsplash.client.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UrlsPhoto {

    @SerializedName("regular")
    @Expose
    var regular: String? = null

    @SerializedName("small")
    @Expose
    var small: String? = null
}
