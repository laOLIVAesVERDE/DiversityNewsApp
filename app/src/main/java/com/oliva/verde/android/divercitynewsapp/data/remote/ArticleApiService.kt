package com.oliva.verde.android.divercitynewsapp.data.remote

import com.oliva.verde.android.divercitynewsapp.BuildConfig
import com.oliva.verde.android.divercitynewsapp.data.remote.dto.ArticleDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("v2/everything/")
    suspend fun getArticles(@Query("apiKey") apiKey: String = BuildConfig.API_KEY,
                            @Query("q") searchWord : String) : ArticleDto
}