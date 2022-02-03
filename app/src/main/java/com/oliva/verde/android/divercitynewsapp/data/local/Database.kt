package com.oliva.verde.android.divercitynewsapp.data.local

import androidx.room.RoomDatabase
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

@Database(entities = [Article.StockArticle::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun stockArticleDao() : StockArticleDao
}