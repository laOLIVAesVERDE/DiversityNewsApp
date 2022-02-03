package com.oliva.verde.android.divercitynewsapp.di

import com.oliva.verde.android.divercitynewsapp.data.remote.ArticleApiService
import com.oliva.verde.android.divercitynewsapp.data.repository.ArticleRepositoryImpl
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideArticleApiService(): ArticleApiService {
        val baseUrl = "https://newsapi.org/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(apiService: ArticleApiService) : ArticleRepository {
        return ArticleRepositoryImpl(apiService)
    }
}
//class ApiModule {
//    @Provides
//    fun provideMoshi() : Moshi {
//        val builder = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//        return builder.build()
//    }
//
//    @Provides
//    fun provideOkHttpClient() : OkHttpClient {
//        val builder = OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//        return  builder.build()
//    }
//
//    @Provides
//    fun provideApiService(client: OkHttpClient, moshi: Moshi) : ArticleApiService {
//        val ENDPOINT = "https://newsapi.org/"
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(ENDPOINT)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .client(client)
//            .build()
//        return retrofit.create(ArticleApiService::class.java)
//    }
//}