package com.oliva.verde.android.divercitynewsapp.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.domain.model.Article

abstract class BaseBindingHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun setViewData(article: Article)
}