package com.oliva.verde.android.divercitynewsapp

object RepositoryFactory {
    fun createRepository() : Repository {
        val apiClient = ApiServiceManager.retrofit.create(ApiService::class.java)
        return Repository(apiClient)
    }
}