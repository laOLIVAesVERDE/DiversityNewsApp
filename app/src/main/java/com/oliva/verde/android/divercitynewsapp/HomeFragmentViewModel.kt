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
    val APIKEY = "413005df5f58476c868396878a752fb8"
    val SEARCHWORD = "bitcoin"

    private val repository = Repository.instance
    var articleListLiveData : MutableLiveData<List<Article>> = MutableLiveData()

    // val articles : LiveData<List<Article>> = _articles

    suspend fun loadArticles() {
        repository.getNewsArticles(APIKEY, SEARCHWORD).also { response ->
            if (response.isSuccessful) {
                articleListLiveData.postValue(response.body())
            }
        }
    }
}