package com.oliva.verde.android.divercitynewsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository

class StockFragmentViewModel : ViewModel() {
    companion object {
        val LOGTAG = "StockFragmentViewModel"
    }

    private val repository = ArticleRepository.instance
    private var _stockArticleListLiveData : MutableLiveData<List<Article.StockArticle>> = MutableLiveData()
    val stockArticleListLiveData : LiveData<List<Article.StockArticle>> = _stockArticleListLiveData

    init {
        viewModelScope.launch {
            getAllStockedArticles()
        }
    }

    suspend fun getAllStockedArticles() {
        Log.d(LOGTAG, "getAllStockedArticles called")
        val stkList = repository.getStockedArticles()
        stkList.forEach {
            Log.d(LOGTAG, it.title)
        }
        _stockArticleListLiveData.postValue(stkList)
    }

    suspend fun deleteTargetArticle(stockArticle: Article.StockArticle) {
        repository.deleteStockArticle(stockArticle)
    }

    /*
    suspend fun deleteTargetArticle(targetArticle: Article) {
        Log.d(LOGTAG, "deleteTargetArticle called")
        repository.deleteTargetArticle(targetArticle)
    }

     */
}