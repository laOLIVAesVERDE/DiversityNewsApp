package com.oliva.verde.android.divercitynewsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.repository.Repository
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import kotlinx.coroutines.launch

class StockFragmentViewModel : ViewModel() {
    companion object {
        val LOGTAG = "StockFragmentViewModel"
    }

    private val repository = Repository.instance
    private var _stockArticleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    val stockArticleListLiveData : LiveData<List<Article>> = _stockArticleListLiveData

    init {
        getAllStockedArticles()
    }

    private fun getAllStockedArticles() {
        Log.d(LOGTAG, "getAllStockedArticles called")
        val stkList = repository.getStockedArticles()
        stkList.forEach {
            Log.d(LOGTAG, it.title)
        }
        _stockArticleListLiveData.postValue(stkList)
    }

    suspend fun deleteTargetArticle(targetArticle: Article) {
        Log.d(LOGTAG, "deleteTargetArticle called")
        repository.deleteTargetArticle(targetArticle)
    }
}