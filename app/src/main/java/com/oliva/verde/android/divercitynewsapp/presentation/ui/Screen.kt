package com.oliva.verde.android.divercitynewsapp.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector?) {
    object WebView: Screen("web_view", null)
    object ArticleList: Screen("article_list", Icons.Filled.Home)
    object StockArticleList: Screen("stock_article_list", Icons.Filled.Favorite)
}
