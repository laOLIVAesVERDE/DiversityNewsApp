package com.oliva.verde.android.divercitynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding

class ArticleAdapter(private val viewModel: HomeFragmentViewModel, private val parentLifecycleOwner : LifecycleOwner) : RecyclerView.Adapter<ArticleAdapter.BindingHolder>() {

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsRowBinding.inflate(layoutInflater, parent, false)
        // 生成したビューホルダをリターンする
        return BindingHolder(binding)
    }

    // ビューホルダ内の各画面部品にデータを割り当てる
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.viewModel = viewModel
        holder.binding.position = position
        holder.binding.lifecycleOwner = parentLifecycleOwner

    }

    override fun getItemCount(): Int {
        return viewModel.articleListLiveData.size
    }

    inner class BindingHolder(var binding: NewsRowBinding) :
        RecyclerView.ViewHolder(binding.root)
}
