package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.ApiService
import com.oliva.verde.android.divercitynewsapp.Repository
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

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
            .addInterceptor(HttpLoggingInterceptor())
        return  builder.build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient, moshi: Moshi) : ApiService {
        val ENDPOINT = "https://newsapi.org/"

        val retrofit = Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService) : Repository {
        return Repository(apiService)
    }
}