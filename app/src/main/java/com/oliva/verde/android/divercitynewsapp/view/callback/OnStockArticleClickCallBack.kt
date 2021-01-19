package com.oliva.verde.android.divercitynewsapp.view.callback

import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.model.StockArticle

interface OnStockArticleClickCallBack {
    fun onItemClick(stockArticle: StockArticle)
    fun onContextClick(stockArticle: StockArticle)
}