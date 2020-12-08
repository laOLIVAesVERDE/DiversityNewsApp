package com.oliva.verde.android.divercitynewsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.verde.android.divercitynewsapp.injection.ApiComponent
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

const val APIKEY = "413005df5f58476c868396878a752fb8"
const val SEARCHWORD = "ダイバーシティ"
class HomeFragmentViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository

    private val _articles : MutableLiveData<List<Article>> = MutableLiveData()
    val articles : LiveData<List<Article>> = _articles

    init {
        DaggerApiComponent.create().inject(this)
        loadArticles()
    }

    fun loadArticles() {

        viewModelScope.launch {
            try {
                val request = repository.getNewsArticles(APIKEY, SEARCHWORD)
                _articles.postValue(request.body())
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}