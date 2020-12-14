package com.oliva.verde.android.divercitynewsapp

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // https://newsapi.org/v2/everything/?apiKey=${apiKey}&q=bitcoin というURLオブジェクトにGETリクエスト
    @GET("v2/everything/") // アノテーション : ソースコード中に登場する要素（クラスやメソッドなど）に対して、コンパイラや実行環境に伝達したい付加的な情報（メタデータ）を注記する仕組み
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Call<ResponseData> // 返り値をSingle型とする(参照：https://qiita.com/takahirom/items/f3e576e91b219c7239e7)

    /**
   @GET("/v2/everything/")
   fun getNews(@Query("apiKey") apiKey: String,
   @Query("q") searchWord : String) : Observable<ResponseData> // 返り値をResponseDataを返すObservable型とする
   // Observable : 観測可能なオブジェクト

   返り値をCallオブジェクトとする場合
    fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Call<ResponseData> // Call : リクエストを送り、レスポンスを返すインターフェース

    */
}