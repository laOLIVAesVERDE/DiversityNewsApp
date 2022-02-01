package com.oliva.verde.android.divercitynewsapp.di

import com.oliva.verde.android.divercitynewsapp.MyApplication
import com.oliva.verde.android.divercitynewsapp.service.repository.Repository
import com.oliva.verde.android.divercitynewsapp.view.adapter.ArticleAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppComponentModule::class, DatabaseModule::class, ApiModule::class])
interface AppComponent {
    fun inject(myApplication: MyApplication)
    fun inject(repository: Repository)
    fun inject(articleAdapter: ArticleAdapter)
}