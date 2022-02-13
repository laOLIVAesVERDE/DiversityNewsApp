package com.oliva.verde.android.divercitynewsapp.presentation.stock_article.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliva.verde.android.divercitynewsapp.presentation.article_list.components.HomeFragmentViewModel

@Composable
fun StockArticleListScreen(
    navController: NavController,
    viewModel: StockFragmentViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
}