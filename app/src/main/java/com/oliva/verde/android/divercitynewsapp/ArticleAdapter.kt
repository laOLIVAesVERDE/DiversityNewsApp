package com.oliva.verde.android.divercitynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding

class ArticleAdapter(var articleList : MutableList<Article>) : RecyclerView.Adapter<ArticleAdapter.BindingHolder>() {

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsRowBinding.inflate(layoutInflater, parent, false)
        // 生成したビューホルダをリターンする
        return BindingHolder(binding)
    }

    // ビューホルダ内の各画面部品にデータを割り当てる
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val article = articleList[position]
        holder.binding.article = article
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class BindingHolder(var binding: NewsRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}
