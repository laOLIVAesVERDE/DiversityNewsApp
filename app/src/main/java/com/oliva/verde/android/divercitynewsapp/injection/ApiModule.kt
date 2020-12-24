package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.service.repository.api.ApiService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiModule {
    @Provides
    fun provideMoshi() : Moshi {
        val builder = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
        return builder.build()
    }

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        return  builder.build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient, moshi: Moshi) : ApiService {
        val ENDPOINT = "https://newsapi.org/"

        val retrofit = Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}