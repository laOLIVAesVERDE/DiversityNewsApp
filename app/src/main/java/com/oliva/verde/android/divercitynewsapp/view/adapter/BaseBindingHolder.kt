package com.oliva.verde.android.divercitynewsapp.view.adapter

import android.view.ContextMenu
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

abstract class BaseBindingHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun setViewData(article: Article)
}