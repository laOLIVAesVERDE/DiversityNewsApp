package com.oliva.verde.android.divercitynewsapp

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.oliva.verde.android.divercitynewsapp.injection.*
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : MultiDexApplication() {

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        appComponent = DaggerAppComponent.builder()
                .appComponentModule(AppComponentModule(this))
                .apiModule(ApiModule())
                .databaseModule(DatabaseModule())
                .build()
    }
}