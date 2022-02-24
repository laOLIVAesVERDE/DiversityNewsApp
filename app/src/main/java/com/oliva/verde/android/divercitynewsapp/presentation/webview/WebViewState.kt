package com.oliva.verde.android.divercitynewsapp.presentation.webview

import com.oliva.verde.android.divercitynewsapp.domain.model.Article

data class WebViewState(
    val isLoading: Boolean = false,
    val url: String = "",
    val error: String = ""
)