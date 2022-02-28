package com.oliva.verde.android.divercitynewsapp.data.local

import androidx.room.*
import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

@Dao
interface StockArticleDao {
    @Query("SELECT * FROM StockArticleDto")
    suspend fun findAll() : List<StockArticleDto>

    /**
     * レコードが存在したら置き換え、存在しなければインサート
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(targetArticle: StockArticleDto)

    @Delete
    suspend fun delete(targetArticle: StockArticleDto)

}