package com.oliva.verde.android.divercitynewsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.service.repository.ArticleRepository


class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        val LOGTAG = "HomeFragmentViewModel"
    }

    private val repository = ArticleRepository.instance
    private var _responseArticleListLiveData : MutableLiveData<List<Article.ResponseArticle>> = MutableLiveData()
    val responseArticleListLiveData : LiveData<List<Article.ResponseArticle>> = _responseArticleListLiveData

    init {
        loadArticles()
    }

    /**
     * ViewModelScope は、アプリで ViewModel ごとに定義される。
     * このスコープ内で起動されたすべてのコルーチンは、ViewModel が消去されると自動的にキャンセルされる。
     * ViewModelがアクティブな場合にのみ行う必要がある作業があるとき、コルーチンが役に立つ。
     * 例：レイアウト用のデータを計算している場合、作業を ViewModel にスコープする必要があるため、
     * 　　ViewModel が消去されると、作業はリソースの消費を避けるために自動的にキャンセルされる。
     */
    private fun loadArticles() = viewModelScope.launch {
        Log.d(LOGTAG, "loadArticles called")
        val response = repository.getNewsArticles(getApplication<Application>().getString(
            R.string.api_key
        ), getApplication<Application>().getString(R.string.search_word))
        if (response.isSuccessful) {
            _responseArticleListLiveData.postValue(response.body()?.articles)
        }
    }

    suspend fun insertTargetArticle(stockArticle: Article.StockArticle) {
        repository.insertArticle(stockArticle)
    }


}