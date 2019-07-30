package com.unsplash.client.network.Api;


import com.unsplash.client.data.model.PhotoModel;
import com.unsplash.client.network.response.ApiResponse;

import java.util.List;

import rx.Observable;

public class Api {

    public static Observable<List<PhotoModel>> getPhotos(String query) {

        return ApiFactory
                .getApiPhotos()
                .getPhotos(query)
                .flatMap(ApiResponse::handleError)
                .map(ApiResponse::getData);

    }
}
