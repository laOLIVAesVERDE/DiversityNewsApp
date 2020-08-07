package com.oliva.verde.android.divercitynewsapp

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class ApiServiceManager {
    companion object {
        val apiService : ApiService
            get() = Retrofit.Builder() // ビルダーオブジェクトを取得
                // Calling baseUrl is required before calling build(). All other methods are optional.
                // build前にbaseUrlが必要となる。他はオプション
                .baseUrl("https://newsapi.org/") // baseurlを指定
                .addConverterFactory(GsonConverterFactory.create()) // JsonオブジェクトをGsonに変換
                // addCallAdapterFactory : Call以外の型を返す
                // RxJava2CallAdapterFactory : Observable型を返すことが可能となる
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java) // Retrofitオブジェクトに、APIサービスインスタンスによって定義されたAPIエンドポイントを実装する
    }
}