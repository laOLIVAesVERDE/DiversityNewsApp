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

    private val realm : Realm = Realm.getDefaultInstance()
    private val stockArticleDao : StockArticleDao = StockArticleDao(realm)
    private val repository = Repository.instance
    var _articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    val articleListLiveData : LiveData<List<Article>> = _articleListLiveData
    var stockArticleList : RealmResults<Article> = stockArticleDao.select()

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        Log.d(LOGTAG, "loadArticles called")
        val response = repository.getNewsArticles(getApplication<Application>().getString(
            R.string.api_key
        ), getApplication<Application>().getString(R.string.search_word))
        if (response.isSuccessful) {
            _articleListLiveData.postValue(response.body()?.articles)
        }
    }


}