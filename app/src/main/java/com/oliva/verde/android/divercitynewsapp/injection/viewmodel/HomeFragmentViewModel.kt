package com.oliva.verde.android.divercitynewsapp.injection.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.service.repository.Repository
import kotlinx.coroutines.launch


class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val LOGTAG = "HomeFragmentViewModel"

    private val repository = Repository.instance
    var _articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    val articleListLiveData : LiveData<List<Article>> = _articleListLiveData

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