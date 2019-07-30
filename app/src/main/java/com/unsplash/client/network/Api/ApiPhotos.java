package com.unsplash.client.network.Api;

import com.unsplash.client.data.model.PhotoModel;
import com.unsplash.client.network.response.ApiResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiPhotos {

    @GET("search/photos")
    Observable<ApiResponse<List<PhotoModel>>> getPhotos(@Query("client_id") String clientId, @Query("page") Int page ,@Query("query") String query);
}
