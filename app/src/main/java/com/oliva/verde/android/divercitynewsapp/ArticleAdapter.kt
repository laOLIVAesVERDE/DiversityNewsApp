package com.oliva.verde.android.divercitynewsapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArticleAdapter(var articleList : MutableList<Article>) : RecyclerView.Adapter<ArticleAdapter.RecycleListViewHolder>() {
    private lateinit var holder : RecycleListViewHolder // 変数を lateinit 宣言することにより、non-null な初期化済みの変数として参照することができる

    // 各アイテムの画面部品が記述されたレイアウトファイルを元にビューホルダオブジェクトを生成する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleListViewHolder {

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

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class RecycleListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageRow : ImageView
        var titleRow : TextView
        var publishDateRow : TextView

        init {
            imageRow = itemView.findViewById(R.id.image_row)
            titleRow = itemView.findViewById(R.id.title_row)
            publishDateRow = itemView.findViewById(R.id.publish_date_row)
        }
    }
}