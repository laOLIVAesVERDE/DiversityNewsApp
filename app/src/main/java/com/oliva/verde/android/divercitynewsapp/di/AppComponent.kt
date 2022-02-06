package com.oliva.verde.android.divercitynewsapp.di

import com.oliva.verde.android.divercitynewsapp.MyApplication
import com.oliva.verde.android.divercitynewsapp.domain.repository.ArticleRepository
import com.oliva.verde.android.divercitynewsapp.presentation.adapter.ArticleAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppComponentModule::class, DatabaseModule::class, ApiModule::class])
interface AppComponent {
    fun inject(myApplication: MyApplication)
    fun inject(articleRepository: ArticleRepository)
    fun inject(articleAdapter: ArticleAdapter)
}