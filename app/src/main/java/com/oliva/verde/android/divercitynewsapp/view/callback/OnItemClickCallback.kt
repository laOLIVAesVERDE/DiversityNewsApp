package com.oliva.verde.android.divercitynewsapp.view.callback

import android.widget.ImageButton
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

interface OnItemClickCallback {
    fun <T> onItemClick(t : T)
    fun <T> onContextClick(t : T)
}

