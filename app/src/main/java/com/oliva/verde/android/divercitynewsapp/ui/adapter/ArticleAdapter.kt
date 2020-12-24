package com.oliva.verde.android.divercitynewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding

class ArticleAdapter() : RecyclerView.Adapter<ArticleAdapter.BindingHolder>() {
    private var articleList : List<Article>? = null

    fun setArticleList(articleList : List<Article>) {

        if (this.articleList == null) {
            this.articleList = articleList
            notifyItemRangeInserted(0, articleList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return  requireNotNull(this@ArticleAdapter.articleList).size
                }

                override fun getNewListSize(): Int {
                    return articleList.size
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldList = this@ArticleAdapter.articleList
                    return oldList?.get(oldItemPosition)?.title == articleList[newItemPosition].title
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newArticle = articleList[newItemPosition]
                    val oldArticle = articleList[oldItemPosition]
                    return newArticle.title == oldArticle.title && newArticle.url == oldArticle.url
                }
            })
            this.articleList = articleList
            result.dispatchUpdatesTo(this)
        }
    }

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate(layoutInflater,
                R.layout.news_row, parent, false)
            as NewsRowBinding
        // 生成したビューホルダをリターンする
        return BindingHolder(
            binding
        )
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
