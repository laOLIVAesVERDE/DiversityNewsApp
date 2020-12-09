package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.HomeFragment
import com.oliva.verde.android.divercitynewsapp.HomeFragmentViewModel
import com.oliva.verde.android.divercitynewsapp.Repository
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(repository: Repository)
}