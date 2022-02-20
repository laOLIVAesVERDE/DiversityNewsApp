package com.oliva.verde.android.divercitynewsapp.data.repository

import com.oliva.verde.android.divercitynewsapp.data.local.dto.StockArticleDto
import com.oliva.verde.android.divercitynewsapp.domain.repository.StockArticleRepository

class TestStockArticleRepositoryImpl : StockArticleRepository {
    private val testArticle = StockArticleDto(
        id = 0,
        url = "https://www.engadget.com/meta-not-threatening-to-leave-europe-204440537.html",
        urlToImage = "https://s.yimg.com/os/creatr-uploaded-images/2022-02/bc20e7a0-891e-11ec-aee6-f7e05ff10a9b",
        publishedAt = "2020-02-20",
        title = "This is test article",
        isReadFlag = false
    )
    private val testStockArticles = mutableListOf<StockArticleDto>().apply {
        for (i in 0 until 10) {
            add(testArticle)
        }
    }
    override suspend fun getStockArticles(): List<StockArticleDto> {
        return testStockArticles
    }
}