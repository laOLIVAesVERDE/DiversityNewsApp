package com.oliva.verde.android.divercitynewsapp.service.repository.api

import com.oliva.verde.android.divercitynewsapp.service.model.Article

// APIリクエスト後のレスポンスクラスを定義
data class ResponseData(val articles : MutableList<Article>)