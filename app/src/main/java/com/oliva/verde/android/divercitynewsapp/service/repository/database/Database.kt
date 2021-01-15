package com.oliva.verde.android.divercitynewsapp.service.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oliva.verde.android.divercitynewsapp.service.model.Article

@Database(entities = [Article::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun stockArticleDao() : StockArticleDao
}