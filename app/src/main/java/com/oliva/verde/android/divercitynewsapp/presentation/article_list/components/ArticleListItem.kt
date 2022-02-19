package com.oliva.verde.android.divercitynewsapp.presentation.article_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

@Composable
fun ArticleListItem(
    article: Article.ResponseArticle,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            painter = rememberImagePainter(article.urlToImage),
            contentDescription = article.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun ArticleDescription(
    article: Article.ResponseArticle
) {
    
}