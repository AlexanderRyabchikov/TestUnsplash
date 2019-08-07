package com.unsplash.client.network.Api

import com.unsplash.client.data.model.PhotoModel
import com.unsplash.client.network.response.ApiResponse

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApiPhotos {

    @GET("search/photos")
    fun getPhotos(@Query("client_id") clientId: String, @Query("page") page: Int, @Query("query") query: String): Observable<ApiResponse<List<PhotoModel>>>
}
