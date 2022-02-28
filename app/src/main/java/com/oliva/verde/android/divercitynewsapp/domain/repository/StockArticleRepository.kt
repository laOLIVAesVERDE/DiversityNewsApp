package com.oliva.verde.android.divercitynewsapp.domain.repository

import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto

interface StockArticleRepository {
    suspend fun getStockArticles(): List<StockArticleDto>
    suspend fun add(dto: StockArticleDto)
    suspend fun delete(dto: StockArticleDto)
}