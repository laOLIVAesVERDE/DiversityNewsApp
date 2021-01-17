package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.service.repository.Repository
import com.oliva.verde.android.divercitynewsapp.view.adapter.ArticleAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppComponentModule::class, DatabaseModule::class, ApiModule::class])
interface AppComponent {
    fun inject(repository: Repository)
    fun inject(articleAdapter: ArticleAdapter)
}