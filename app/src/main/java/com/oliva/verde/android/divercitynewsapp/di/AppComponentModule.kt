package com.oliva.verde.android.divercitynewsapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppComponentModule(val mApplication : Application) {
    @Provides
    @Singleton
    fun provideApplication() : Application {
        return mApplication
    }
}