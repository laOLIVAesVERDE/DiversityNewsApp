package com.oliva.verde.android.divercitynewsapp.presentation.stock_article.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliva.verde.android.divercitynewsapp.common.Util
import com.oliva.verde.android.divercitynewsapp.presentation.ui.Screen

@Composable
fun StockArticleListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: StockArticleViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.stockArticles) { stockArticle ->
                StockArticleListItem(stockArticle = stockArticle) {
                    navController.navigate(
                        Screen.WebView.route + "/${Util.replaceSlashToHyphen(stockArticle.url)}"
                    )
                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}