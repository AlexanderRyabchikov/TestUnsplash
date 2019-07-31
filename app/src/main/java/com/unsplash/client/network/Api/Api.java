package com.unsplash.client.network.Api;

import com.unsplash.client.BuildConfig;
import com.unsplash.client.data.model.PhotoModel;
import com.unsplash.client.network.response.ApiResponse;

import java.util.List;
import java.util.Objects;

import rx.Observable;

public class Api {

    public static Observable<List<PhotoModel>> getPhotos(int page, String query) {

        return Objects.requireNonNull(ApiFactory
                .getApiPhotos())
                .getPhotos(BuildConfig.UserAccessKey, page, query)
                .flatMap(ApiResponse::handleError)
                .map(ApiResponse::getData);

    }
}
