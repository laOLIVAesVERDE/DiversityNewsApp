package com.oliva.verde.android.divercitynewsapp.presentation.webview.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.oliva.verde.android.divercitynewsapp.common.Constants
import com.oliva.verde.android.divercitynewsapp.common.Util
import com.oliva.verde.android.divercitynewsapp.presentation.webview.WebViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(WebViewState())
    val state : State<WebViewState> = _state

    init {
        loadUrl()
    }

    private fun loadUrl() {
        _state.value = WebViewState(isLoading = true)
        val url = savedStateHandle.get<String>(Constants.PARAM_WEB_VIEW_URL)
        Log.e("confirm", "web view url : $url")
        if (url != null) {
            _state.value = WebViewState(url = Util.replaceHyphenToSlash(url))
        } else {
            _state.value = WebViewState(error = "An unexpected error occurred")
        }
    }
}