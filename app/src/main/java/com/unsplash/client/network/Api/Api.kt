package com.unsplash.client.network.Api

import com.unsplash.client.BuildConfig
import com.unsplash.client.data.model.PhotoModel
import com.unsplash.client.network.response.ApiResponse
import java.util.Objects

import rx.Observable

class Api {

    companion object {

       fun getPhotos(page: Int, query: String): Observable<List<PhotoModel>>? {

        return ApiFactory
                .apiPhotos
                ?.getPhotos(BuildConfig.UserAccessKey, page, query)
                ?.flatMap{ it.handleError() }
                ?.map { it.data }
       }

    }
}
