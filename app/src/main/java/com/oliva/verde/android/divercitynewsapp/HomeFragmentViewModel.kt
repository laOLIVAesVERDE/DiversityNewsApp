package com.oliva.verde.android.divercitynewsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oliva.verde.android.divercitynewsapp.injection.ApiComponent
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject



class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val LOGTAG = "HomeFragmentViewModel"

    private val repository = Repository.instance
    var articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    // val articleListLiveData : LiveData<List<Article>> = _articleListLiveData
    // val articles : LiveData<List<Article>> = _articles

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        try {
            Log.d(LOGTAG, "loadArticles called")
            val response = repository
                .getNewsArticles(
                    getApplication<Application>().getString(R.string.api_key),
                    getApplication<Application>().getString(R.string.search_word))
            if (response.isSuccessful) {
                Log.d(LOGTAG, "response is successful")
                articleListLiveData.postValue(response.body()?.articles)
            }
        } catch (e: Exception) {
            Log.d(LOGTAG, "response is failure")
            Log.d(LOGTAG, "Error detail : ${e.toString()}")
            e.stackTrace
        }
    }
}