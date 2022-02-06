package com.oliva.verde.android.divercitynewsapp.data.repository

import com.oliva.verde.android.divercitynewsapp.data.local.StockArticleDao
import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto
import com.oliva.verde.android.divercitynewsapp.data.remote.ArticleApiService
import com.oliva.verde.android.divercitynewsapp.data.remote.dto.ArticleDto
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
  private val api: ArticleApiService,
  private val dao: StockArticleDao
) : ArticleRepository {
  override suspend fun getArticles(searchWord: String): List<ArticleDto> {
    return api.getArticles(searchWord = searchWord)
  }

  override suspend fun getStockArticles(): List<StockArticleDto> {
    return dao.findAll()
  }
}