package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.service.repository.Repository
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun inject(repository: Repository)
}