package com.oliva.verde.android.divercitynewsapp.data.repository

import com.oliva.verde.android.divercitynewsapp.data.local.StockArticleDao
import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto
import com.oliva.verde.android.divercitynewsapp.domain.repository.StockArticleRepository
import javax.inject.Inject

class StockArticleRepositoryImpl @Inject constructor(
    private val dao: StockArticleDao
) : StockArticleRepository {
    override suspend fun getStockArticles(): List<StockArticleDto> {
        return dao.findAll()
    }
}