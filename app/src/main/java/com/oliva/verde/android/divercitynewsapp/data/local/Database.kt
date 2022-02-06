package com.oliva.verde.android.divercitynewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto

@Database(entities = [StockArticleDto::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun stockArticleDao() : StockArticleDao
}