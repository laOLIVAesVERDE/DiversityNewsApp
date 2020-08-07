package com.oliva.verde.android.divercitynewsapp

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    open val retrofit: Retrofit  // Retrofitオブジェクトの生成
        get() = Retrofit.Builder() // ビルダーオブジェクトを取得
            // Calling baseUrl is required before calling build(). All other methods are optional.
            // build前にbaseUrlが必要となる。他はオプション
            .baseUrl("https://newsapi.org/") // baseurlを指定
            .addConverterFactory(GsonConverterFactory.create()) // JsonオブジェクトをGsonに変換
            // addCallAdapterFactory : Call以外の型を返す
            // RxJava2CallAdapterFactory : Observable型を返すことが可能となる
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    // https://newsapi.org/v2/everything/?apiKey=${apiKey}&q=ダイバーシティ というURLオブジェクトにGETリクエスト
    @GET("/v2/everything/") // アノテーション : ソースコード中に登場する要素（クラスやメソッドなど）に対して、コンパイラや実行環境に伝達したい付加的な情報（メタデータ）を注記する仕組み
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Observable<ResponseData> // 返り値をResponseDataを返すObservable型とする
                                                                            // Observable : 観測可能なオブジェクト

    /** 返り値をCallオブジェクトとする場合
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Call<ResponseData> // Call : リクエストを送り、レスポンスを返すインターフェース

    */
}