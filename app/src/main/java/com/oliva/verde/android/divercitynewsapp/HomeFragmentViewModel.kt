package com.oliva.verde.android.divercitynewsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.oliva.verde.android.divercitynewsapp.injection.ApiComponent
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val LOGTAG = "HomeFragmentViewModel"

    private val repository = Repository.instance
    var articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    // val articleListLiveData : LiveData<List<Article>> = _articleListLiveData

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        Log.d(LOGTAG, "loadArticles called")
        val response = repository.getNewsArticles(getApplication<Application>().getString(R.string.api_key), getApplication<Application>().getString(R.string.search_word))
        if (response.isSuccessful) {
            articleListLiveData.postValue(response.body())
        }

    }
}