package com.oliva.verde.android.divercitynewsapp.presentation.callback

interface OnItemClickCallback {
    fun <T> onItemClick(t : T)
    fun <T> onContextClick(t : T)
}

