package com.oliva.verde.android.divercitynewsapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oliva.verde.android.divercitynewsapp.data.local.Database
import com.oliva.verde.android.divercitynewsapp.data.local.StockArticleDao
import com.oliva.verde.android.divercitynewsapp.data.remote.ArticleApiService
import com.oliva.verde.android.divercitynewsapp.data.repository.ArticleRepositoryImpl
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDataBase(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(context, Database::class.java, "stockedArticle")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Provides
    @Singleton
    fun provideStockArticleDao(database: Database) : StockArticleDao {
        return database.stockArticleDao()
    }

    @Provides
    @Singleton
    fun provideArticleRepository(apiService: ArticleApiService, dao: StockArticleDao) : ArticleRepository {
        return ArticleRepositoryImpl(apiService, dao)
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