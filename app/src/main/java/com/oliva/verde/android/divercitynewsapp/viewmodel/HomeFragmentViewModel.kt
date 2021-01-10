package com.oliva.verde.android.divercitynewsapp.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.*
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.service.repository.Repository
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.launch


class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val LOGTAG = "HomeFragmentViewModel"

    private val repository = Repository.instance
    private var _articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    val articleListLiveData : LiveData<List<Article>> = _articleListLiveData

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
            _articleListLiveData.postValue(response.body()?.articles)
        }
    }

    fun insertTargetArticle(article: Article) = viewModelScope.launch {
        repository.stockArticleDao.insert(article)
    }

}