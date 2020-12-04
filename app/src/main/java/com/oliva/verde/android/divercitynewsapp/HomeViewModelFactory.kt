package com.oliva.verde.android.divercitynewsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {
    /*
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(repository) as T
    }

     */
}