package com.oliva.verde.android.divercitynewsapp

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceManager {
        val ENDPOINT = "https://newsapi.org/"
        val retrofit : Retrofit

        init {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val builder = Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
            retrofit = builder.build()
        }
}