package com.oliva.verde.android.divercitynewsapp.presentation.article_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ArticleListScreen(
    navController: NavController,
    viewModel: ArticleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        
    }
}