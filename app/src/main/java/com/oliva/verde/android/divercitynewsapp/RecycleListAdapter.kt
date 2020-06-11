package com.oliva.verde.android.divercitynewsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecycleListAdapter(fragment : Any, articleList : MutableList<Article>) : RecyclerView.Adapter<RecycleListViewHolder>() {
    private var fragment = fragment
    private var articleList = articleList
    private lateinit var holder : RecycleListViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleListViewHolder {
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
        return holder
    }

    override fun onBindViewHolder(holder: RecycleListViewHolder, position: Int) {
        val article = articleList[position]
        val title = article.title
        val publishedAt = article.publishedAt
        holder.titleRow.text = title
        holder.publishDateRow.text = publishedAt.substring(0..9)
        Picasso.get().load(article.urlToImage).into(holder.imageRow)
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

        // holder.itemView.setOnClickListener(fragment.ListItemClickListener(position))
        // holder.itemView.setOnLongClickListener(ListItemLongClickListener(position))
        // fragment.registerForContextMenu(holder.itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}