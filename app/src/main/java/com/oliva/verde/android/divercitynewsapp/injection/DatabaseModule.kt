package com.oliva.verde.android.divercitynewsapp.injection

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oliva.verde.android.divercitynewsapp.service.repository.database.Database
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(application: Application) : RoomDatabase {
        return Room.databaseBuilder(application.applicationContext, RoomDatabase::class.java, "stockedArticle")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Provides
    fun provideStockArticleDao(database: Database) : StockArticleDao {
        return database.stockArticleDao()
    }
}