package com.unsplash.client.network.Api;


import androidx.annotation.NonNull;

import com.unsplash.client.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final int TIMEOUT = 5;
    private static final int WRITE_TIMEOUT = 5;
    private static final int CONNECT_TIMEOUT = 5;
    private static final HttpLoggingInterceptor LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final String API_ENDPOINT = BuildConfig.ServerUrl;

    public static ApiPhotos getApiPhotos() {
        return getRetrofit().create(ApiPhotos.class);
    }

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor(LOGGING_INTERCEPTOR)
            .addInterceptor(ApiFactory::onOnIntercept)
            .build();

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();
    }

    private static Response onOnIntercept(Interceptor.Chain chain) throws IOException {
        try {
            return chain.proceed(chain.request());
        } catch (IOException exception) {
            throw exception;
        }
    }
}
