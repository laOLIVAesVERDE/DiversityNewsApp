package com.oliva.verde.android.divercitynewsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(val repository: Repository) : ViewModel() {
    private val _articles : MutableLiveData<List<Article>> = MutableLiveData()
    val articles : LiveData<List<Article>> = _articles

    

}