package com.oliva.verde.android.divercitynewsapp.presentation.article_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

@Composable
fun ArticleListItem(
    article: Article.ResponseArticle,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

    }
}