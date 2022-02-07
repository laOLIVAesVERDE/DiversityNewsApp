package com.oliva.verde.android.divercitynewsapp.presentation.stock_article

import com.oliva.verde.android.divercitynewsapp.domain.model.Article

data class StockArticleListState(
    val isLoading: Boolean = false,
    val articles: List<Article.StockArticle> = emptyList(),
    val error: String = ""
)
