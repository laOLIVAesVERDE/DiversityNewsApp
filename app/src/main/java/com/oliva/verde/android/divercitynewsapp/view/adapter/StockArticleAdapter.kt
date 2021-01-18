package com.oliva.verde.android.divercitynewsapp.view.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding
import com.oliva.verde.android.divercitynewsapp.databinding.StockNewsRowBinding
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.service.model.StockArticle
import com.oliva.verde.android.divercitynewsapp.view.callback.OnItemClickCallback

class StockArticleAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<StockArticleAdapter.BindingHolder>() {
    private var mStockArticleList : List<StockArticle>? = null


    fun setArticleList(stockArticleList : List<StockArticle>) {

        if (this.mStockArticleList == null) {
            this.mStockArticleList = stockArticleList
            notifyItemRangeInserted(0, stockArticleList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return  requireNotNull(this@StockArticleAdapter.mStockArticleList).size
                }

                override fun getNewListSize(): Int {
                    return stockArticleList.size
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldList = this@StockArticleAdapter.mStockArticleList
                    return oldList?.get(oldItemPosition)?.title == stockArticleList[newItemPosition].title
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newArticle = stockArticleList[newItemPosition]
                    val oldArticle = stockArticleList[oldItemPosition]
                    return newArticle.title == oldArticle.title && newArticle.url == oldArticle.url
                }
            })
            this.mStockArticleList = stockArticleList
            result.dispatchUpdatesTo(this)
        }
    }

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate(layoutInflater,
                R.layout.stock_news_row, parent, false) as StockNewsRowBinding
        binding.onItemClickCallback = onItemClickCallback
        // 生成したビューホルダをリターンする
        return BindingHolder(binding)
    }

    // ビューホルダ内の各画面部品にデータを割り当てる
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.stockArticle = mStockArticleList?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return mStockArticleList?.size ?: 0
    }

    open class BindingHolder(var binding: StockNewsRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            binding.imageButton.setOnCreateContextMenuListener(this)
        }
    }
}