package com.oliva.verde.android.divercitynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecycleListAdapter(fragment : Any, articleList : MutableList<Article>) : RecyclerView.Adapter<RecycleListViewHolder>() {
    private var fragment = fragment
    private var articleList = articleList
    private lateinit var holder : RecycleListViewHolder // 変数を lateinit 宣言することにより、non-null な初期化済みの変数として参照することができる

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleListViewHolder {
        // fragmentクラスの違いで処理を分岐
        if(fragment is HomeFragment) {
            val inflater = LayoutInflater.from((fragment as HomeFragment).activity)
            val view = inflater.inflate(R.layout.news_row, parent, false)
            holder = RecycleListViewHolder(view)
        }
        else if(fragment is StockFragment) {
            val inflater = LayoutInflater.from((fragment as StockFragment).activity)
            val view = inflater.inflate(R.layout.news_row, parent, false)
            holder = RecycleListViewHolder(view)
        }
        // 生成したビューホルダをリターンする
        return holder
    }

    // ビューホルダ内の各画面部品にデータを割り当てる
    override fun onBindViewHolder(holder: RecycleListViewHolder, position: Int) {
        val article = articleList[position]
        val title = article.title
        val publishedAt = article.publishedAt
        holder.titleRow.text = title
        holder.publishDateRow.text = publishedAt.substring(0..9)
        Picasso.get().load(article.urlToImage).into(holder.imageRow)
        // fragmentクラスの違いで処理を分岐
        if(fragment is HomeFragment) {
            holder.itemView.setOnClickListener((fragment as HomeFragment).ListItemClickListener(position))
            holder.itemView.setOnLongClickListener((fragment as HomeFragment).ListItemLongClickListener(position))
            (fragment as HomeFragment).registerForContextMenu(holder.itemView)
        }
        else if(fragment is StockFragment) {
            holder.itemView.setOnClickListener((fragment as StockFragment).ListItemClickListener(position))
            holder.itemView.setOnLongClickListener((fragment as StockFragment).ListItemLongClickListener(position))
            (fragment as StockFragment).registerForContextMenu(holder.itemView)
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}