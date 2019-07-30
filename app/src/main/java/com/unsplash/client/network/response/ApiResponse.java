package com.unsplash.client.network.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import rx.Observable;

public class ApiResponse<T> {

    @SerializedName("error")
    private ApiError error;

    @Nullable
    @SerializedName("results")
    private T data;

    @Nullable
    public T getData() {
        return data;
    }

    private boolean isSuccess() {
        return data != null;
    }


    public Observable<ApiResponse<T>> handleError() {
        if (isSuccess()) {
            return Observable.just(this);
        } else {
            return Observable.error(error);
        }
    }
}
