package com.oliva.verde.android.divercitynewsapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oliva.verde.android.divercitynewsapp.data.local.Database
import com.oliva.verde.android.divercitynewsapp.data.local.StockArticleDao
import dagger.Module
import dagger.Provides


@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(mApplication: Application) : Database {
        return Room.databaseBuilder(mApplication.applicationContext, Database::class.java, "stockedArticle")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }


    @Provides
    fun provideStockArticleDao(database: Database) : StockArticleDao {
        return database.stockArticleDao()
    }
}