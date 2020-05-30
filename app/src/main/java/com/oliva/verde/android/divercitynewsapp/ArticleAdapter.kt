package com.oliva.verde.android.divercitynewsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_row.view.*

// 第一引数 : コンテキスト。MainActivityが入る
// 第二引数 : articleリストオブジェクト
// BaseAdpterを継承することで、独自にカスタムしたadapterを作成できる
class ArticleAdapter(val context : Context, val articles : List<Article>) : BaseAdapter() {
    // layoutInflaterオブジェクトを取得
    // 今回はlayoutファイル(news_row.xml)をviewオブジェクトに「吹き込む」(inflate)
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // 下記のゲッターは抽象メソッドなのでオーバーライドが必要
    override fun getCount(): Int {
        return articles.count()
    }

    override fun getItem(position: Int): Any {
        return articles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // getView : データセット内の特定の一のデータを表示するViewオブジェクトを取得する
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // inflateするlayoutファイルを指定
        val view = layoutInflater.inflate(R.layout.news_row, parent, false)
        // layoutファイル内の各画面部品に、データ内のプロパティを割り当てる
        Picasso.get().load(articles[position].urlToImage).into(view.image_row)
        view.title_row.text = articles[position].title
        view.publish_date_row.text = articles[position].publishedAt.substring(0, 10)
        return view
    }
}