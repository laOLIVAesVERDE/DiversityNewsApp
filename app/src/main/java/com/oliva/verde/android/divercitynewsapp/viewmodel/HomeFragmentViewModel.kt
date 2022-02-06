package com.oliva.verde.android.divercitynewsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import com.oliva.verde.android.divercitynewsapp.domain.use_case.get_articles.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    companion object {
        val LOGTAG = "HomeFragmentViewModel"
    }

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
    private fun loadArticles() {
        getArticlesUseCase().onEach { result ->
            
        }
    }

    suspend fun insertTargetArticle(stockArticle: Article.StockArticle) {
//        repository.insertArticle(stockArticle)
    }


}