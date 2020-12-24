package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.model.Repository
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(repository: Repository)
}