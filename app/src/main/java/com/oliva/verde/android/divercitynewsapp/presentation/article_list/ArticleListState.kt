package com.oliva.verde.android.divercitynewsapp.presentation.article_list

import com.oliva.verde.android.divercitynewsapp.domain.model.Article

data class ArticleListState(
    val isLoading: Boolean = false,
    val isAddSuccess: Boolean = false,
    val articles: List<Article.ResponseArticle> = emptyList(),
    val error: String = ""
)
