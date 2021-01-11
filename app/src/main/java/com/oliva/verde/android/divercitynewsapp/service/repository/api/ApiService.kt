package com.oliva.verde.android.divercitynewsapp.service.repository.api

import com.oliva.verde.android.divercitynewsapp.service.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything/")
    // suspend :    適用された関数はメインスレッドをブロックせずに実行できるようになる
    //              また、Coroutine内または他のSuspendingFunction内からしか呼び出すことができない
    suspend fun getNews(@Query("apiKey") apiKey: String,
                @Query("q") searchWord : String) : Response<ResponseData>

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