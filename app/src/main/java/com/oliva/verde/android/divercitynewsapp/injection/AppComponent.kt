package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.view.adapter.ArticleAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppComponentModule::class])
interface AppComponent {
    fun inject(articleAdapter: ArticleAdapter)
}