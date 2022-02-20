package com.oliva.verde.android.divercitynewsapp.presentation.stock_article.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

@Composable
fun StockArticleListItem(
    stockArticle: Article.StockArticle,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(stockArticle.urlToImage),
            contentDescription = stockArticle.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        StockArticleDescription(stockArticle = stockArticle)
    }
}

@Composable
private fun StockArticleDescription(
    modifier: Modifier = Modifier,
    stockArticle: Article.StockArticle
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = stockArticle.title,
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stockArticle.publishedAt,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun PreviewStockArticleListItem() {
    val stockArticle = Article.StockArticle(
        id = 0,
        url = "https://www.engadget.com/meta-not-threatening-to-leave-europe-204440537.html",
        urlToImage = "https://s.yimg.com/os/creatr-uploaded-images/2022-02/bc20e7a0-891e-11ec-aee6-f7e05ff10a9b",
        publishedAt = "2020-02-20",
        title = "This is test article",
        isReadFlag = false
    )
    StockArticleListItem(stockArticle = stockArticle) {}
}
