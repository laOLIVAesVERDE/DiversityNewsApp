package com.oliva.verde.android.divercitynewsapp.view.callback

import com.oliva.verde.android.divercitynewsapp.service.model.Article

interface OnItemClickCallback {
    fun onClick(article: Article)
}

