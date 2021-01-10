package com.oliva.verde.android.divercitynewsapp

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.oliva.verde.android.divercitynewsapp.injection.AppComponent
import com.oliva.verde.android.divercitynewsapp.injection.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration
                .Builder()
                .inMemory()
                .build())
    }

}