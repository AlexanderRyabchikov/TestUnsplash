package com.unsplash.client.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

class ApiError : Throwable() {

    @SerializedName("code")
    @Expose
    var code: Int = 0

    @SerializedName("message")
    @Expose
    var description: String = ""

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun fromErrorObject(error: Any): ApiError {

            val result = ApiError()

            val map: LinkedTreeMap<String, *> = error as LinkedTreeMap<String, *>

            result.code = (map["code"] as Double).toInt()
            result.description = map["message"] as String

            return result
        }
    }
}
