package com.unsplash.client.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import rx.Observable

class ApiResponse<T> {

    @SerializedName("error")
    @Expose
    var error: ApiError? = null

    @SerializedName("results")
    @Expose
    var data: T? = null

    private val isSuccess: Boolean
        get() = data != null


    fun handleError(): Observable<ApiResponse<T>> {
        return if (isSuccess) {
            Observable.just(this)
        } else {
            Observable.error(error)
        }
    }
}
