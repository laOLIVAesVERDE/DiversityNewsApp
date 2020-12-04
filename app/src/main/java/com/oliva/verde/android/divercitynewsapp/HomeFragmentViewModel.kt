package com.oliva.verde.android.divercitynewsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oliva.verde.android.divercitynewsapp.injection.ApiComponent
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository
    private val _articles : MutableLiveData<List<Article>> = MutableLiveData()
    val articles : LiveData<List<Article>> = _articles

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getArticles(apiKey : String, searchWord : String) {
        repository.getNewsArticles(apiKey, searchWord)
            .subscribe { articles : List<Article> ->
                _articles.postValue(articles)
            }
    }
}