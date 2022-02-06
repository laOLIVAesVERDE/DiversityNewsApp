package com.oliva.verde.android.divercitynewsapp.presentation.adapter

import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.domain.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding
import com.oliva.verde.android.divercitynewsapp.databinding.StockNewsRowBinding
import com.oliva.verde.android.divercitynewsapp.presentation.callback.OnItemClickCallback


class ArticleAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<BaseBindingHolder>() {
    companion object {
        private const val VIEW_TYPE_RESPONSE_ARTICLE = R.layout.news_row
        private const val VIEW_TYPE_STOCK_ARTICLE = R.layout.stock_news_row
    }

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

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldList = this@ArticleAdapter.articleList
                    val oldItem = oldList?.get(oldItemPosition)
                    val newItem = articleList[newItemPosition]
                    var resultFlag = false
                    when(oldItem) {
                        is Article.ResponseArticle -> {
                            when(newItem) {
                                is Article.ResponseArticle -> {
                                    resultFlag =  oldItem.title == newItem.title
                                }
                            }
                        }
                        is Article.StockArticle -> {
                            when (newItem) {
                                is Article.StockArticle -> {
                                    resultFlag = oldItem.title == newItem.title
                                }
                            }
                        }
                    }
                    return resultFlag
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = articleList[newItemPosition]
                    val oldItem = articleList[oldItemPosition]
                    var resultFlag = false

                    when(oldItem) {
                        is Article.ResponseArticle -> {
                            when(newItem) {
                                is Article.ResponseArticle -> {
                                    resultFlag =  (oldItem.title == newItem.title && oldItem.url == newItem.url)
                                }
                            }
                        }
                        is Article.StockArticle -> {
                            when (newItem) {
                                is Article.StockArticle -> {
                                    resultFlag =  (oldItem.title == newItem.title && oldItem.url == newItem.url)
                                }
                            }
                        }
                    }
                    return resultFlag
                }
            })
            this.articleList = articleList
            result.dispatchUpdatesTo(this)
        }
    }

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        return when (viewType) {
            VIEW_TYPE_RESPONSE_ARTICLE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false) as NewsRowBinding
                binding.onItemClickCallback = onItemClickCallback
                // 生成したビューホルダをリターンする
                ResponseArticleBindingHolder(binding)
            }
            VIEW_TYPE_STOCK_ARTICLE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false) as StockNewsRowBinding
                binding.onItemClickCallBack = onItemClickCallback
                StockArticleBindingHolder(binding)
            }
            else -> throw IllegalStateException("Bad view type!!")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (articleList?.get(position)) {
            is Article.ResponseArticle -> VIEW_TYPE_RESPONSE_ARTICLE
            is Article.StockArticle -> VIEW_TYPE_STOCK_ARTICLE
            else -> throw java.lang.IllegalStateException("Bad ViewType")
        }
    }

    override fun getItemCount(): Int {
        return articleList?.size ?: 0
    }

    // ビューホルダ内の各画面部品にデータを割り当てる
    override fun onBindViewHolder(holder: BaseBindingHolder, position: Int) {
        val article = articleList?.get(position)
        when (article) {
            is Article.ResponseArticle -> {
                holder as ResponseArticleBindingHolder
                holder.binding.responseArticle = article
            }
            is Article.StockArticle -> {
                holder as StockArticleBindingHolder
                holder.binding.stockArticle = article
            }
        }
    }

    private class ResponseArticleBindingHolder(val binding: NewsRowBinding) : BaseBindingHolder(binding), View.OnCreateContextMenuListener {
        override fun setViewData(article: Article) {
            article as Article.ResponseArticle
        }

        override fun onCreateContextMenu(
            p0: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {
            binding.imageButton.setOnCreateContextMenuListener(this)
        }
    }

    private class StockArticleBindingHolder(val binding: StockNewsRowBinding) : BaseBindingHolder(binding), View.OnCreateContextMenuListener {
        override fun setViewData(article: Article) {
            article as Article.StockArticle
        }

        override fun onCreateContextMenu(
            p0: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {
            binding.imageButton.setOnCreateContextMenuListener(this)
        }
    }


    open class BindingHolder(var binding: NewsRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            binding.imageButton.setOnCreateContextMenuListener(this)
        }
    }
}
