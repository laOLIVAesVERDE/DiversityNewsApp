package com.oliva.verde.android.divercitynewsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_row.view.*

class ArticleAdapter(val context : Context, val articles : List<Article>) : BaseAdapter() {
    // layoutInflaterオブジェクトを取得
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return articles.count()
    }

    override fun getItem(position: Int): Any {
        return articles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.news_row, parent, false)
        Picasso.get().load(articles[position].urlToImage).into(view.image_row)
        view.title_row.text = articles[position].title
        view.publish_date_row.text = articles[position].publishedAt
        return view
    }
}