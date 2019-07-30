package com.unsplash.client.network.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {

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


}
