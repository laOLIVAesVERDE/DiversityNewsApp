package com.oliva.verde.android.divercitynewsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.verde.android.divercitynewsapp.injection.ApiComponent
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

const val LOGTAG = "HomeFragmentViewModel"
const val APIKEY = "413005df5f58476c868396878a752fb8"
const val SEARCHWORD = "ダイバーシティ"

class HomeFragmentViewModel : ViewModel() {
    private val repository = Repository.instance
    var articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()

    // val articles : LiveData<List<Article>> = _articles

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
            Log.d(LOGTAG, "loadArticiles")
            try {
                val response = repository.getNewsArticles(APIKEY, SEARCHWORD)
                articleListLiveData.postValue(response.body())
            } catch (e: Exception) {
                e.stackTrace
            }
    }
}