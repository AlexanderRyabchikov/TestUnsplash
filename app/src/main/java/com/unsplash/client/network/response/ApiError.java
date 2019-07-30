package com.unsplash.client.network.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

public class ApiError extends Throwable {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    static ApiError fromErrorObject(Object error) {

        ApiError result = new ApiError();

        LinkedTreeMap map = (LinkedTreeMap) error;

        result.code = (int)((double) map.get("code"));
        result.description = (String) map.get("message");

        return result;
    }
}
