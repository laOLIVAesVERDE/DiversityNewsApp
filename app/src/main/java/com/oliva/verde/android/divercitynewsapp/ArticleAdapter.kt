package com.oliva.verde.android.divercitynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding

class ArticleAdapter() : RecyclerView.Adapter<ArticleAdapter.BindingHolder>() {
    private var articleList : List<Article>? = null

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.news_row, parent, false)
            as NewsRowBinding
        // 生成したビューホルダをリターンする
        return BindingHolder(binding)
    }

    // ビューホルダ内の各画面部品にデータを割り当てる
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.article = articleList?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    open class BindingHolder(var binding: NewsRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}
