package com.unsplash.client.network.Api


import com.unsplash.client.BuildConfig
import com.unsplash.client.network.Ssl.TLSSocketFactory

import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    companion object {

        private const val TIMEOUT = 5
        private const val WRITE_TIMEOUT = 5
        private const val CONNECT_TIMEOUT = 5
        private val LOGGING_INTERCEPTOR = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private const val API_ENDPOINT = BuildConfig.ServerUrl

        val apiPhotos: ApiPhotos?
            get() {
                try {
                    return retrofit.create(ApiPhotos::class.java)
                } catch (e: KeyManagementException) {
                    e.printStackTrace()
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                }

                return null
            }

        private val retrofit: Retrofit
            @Throws(KeyManagementException::class, NoSuchAlgorithmException::class)
            get() = Retrofit.Builder()
                    .baseUrl(API_ENDPOINT)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(initOkHttpClient())
                    .build()


        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        private fun initOkHttpClient(): OkHttpClient {

            return OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
                    .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MINUTES)
                    .readTimeout(TIMEOUT.toLong(), TimeUnit.MINUTES)
                    .addInterceptor(LOGGING_INTERCEPTOR)
                    .sslSocketFactory(TLSSocketFactory())
                    .addInterceptor { onOnIntercept(it) }
                    .build()
        }

        @Throws(IOException::class)
        private fun onOnIntercept(chain: Interceptor.Chain): Response {
            try {
                return chain.proceed(chain.request())
            } catch (exception: IOException) {
                throw exception
            }

        }
    }
}
