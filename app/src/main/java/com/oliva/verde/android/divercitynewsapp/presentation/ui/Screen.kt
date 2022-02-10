package com.oliva.verde.android.divercitynewsapp.presentation.ui

sealed class Screen(val route: String) {
    object ArticleList: Screen("article_list")
    object StockArticleList: Screen("stock_article_list")
}
