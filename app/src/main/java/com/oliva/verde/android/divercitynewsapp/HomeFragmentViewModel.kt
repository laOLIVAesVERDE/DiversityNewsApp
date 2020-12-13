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



class HomeFragmentViewModel : ViewModel() {
    val LOGTAG = "HomeFragmentViewModel"


    private val repository = Repository.instance
    private val _articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    val articleListLiveData : LiveData<List<Article>> = _articleListLiveData

    // val articles : LiveData<List<Article>> = _articles

    fun loadArticles(apiKey : String, searchWord : String) {
        repository.getNewsArticles(apiKey, searchWord)
            .subscribe { articles : List<Article> ->
                _articleListLiveData.postValue(articles)
            }
    }
}