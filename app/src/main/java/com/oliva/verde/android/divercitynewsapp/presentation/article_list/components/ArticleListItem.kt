package com.oliva.verde.android.divercitynewsapp.presentation.article_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Image(
            painter = rememberImagePainter(article.urlToImage),
            contentDescription = article.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.FillBounds
        )
        ArticleDescription(article = article)
    }
}

@Composable
fun ArticleDescription(
    modifier: Modifier = Modifier,
    article: Article.ResponseArticle
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = article.title,
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = article.publishedAt,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}