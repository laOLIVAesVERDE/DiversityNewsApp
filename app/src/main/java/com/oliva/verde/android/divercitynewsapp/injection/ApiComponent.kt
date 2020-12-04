package com.oliva.verde.android.divercitynewsapp.injection

import com.oliva.verde.android.divercitynewsapp.HomeFragment
import com.oliva.verde.android.divercitynewsapp.HomeFragmentViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(homeFragment: HomeFragment)
}