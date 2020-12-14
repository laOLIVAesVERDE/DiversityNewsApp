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
    private val articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()
    // val articleListLiveData : LiveData<List<Article>> = _articleListLiveData

    // val articles : LiveData<List<Article>> = _articles

    fun loadArticles() = viewModelScope.launch {
        try {
            val response = repository.getNewsArticles(getApplication<Application>().getString(R.string.))
        }
    }
}