package com.oliva.verde.android.divercitynewsapp.view.callback

import android.widget.ImageButton
import com.oliva.verde.android.divercitynewsapp.service.model.Article

interface OnItemClickCallback {
    fun onItemClick(article: Article)
    fun onContextClick(article: Article)
}

