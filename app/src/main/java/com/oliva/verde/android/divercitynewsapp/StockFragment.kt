package com.oliva.verde.android.divercitynewsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class StockFragment : Fragment() {
    var articleList = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stock, container, false)
        // 以下、データベースに登録してある記事をリストビューに表示する処理
        val helper = DataBaseHelper(activity!!)
        val db = helper.writableDatabase
        val sql = "SELECT * FROM stocked_articles"
        val cursor = db.rawQuery(sql, null)
        var title = ""
        var publishedAt = ""
        var urlToImage = ""
        var url = ""

        while(cursor.moveToNext()) {
            val idxTitle = cursor.getColumnIndex("title")
            title = cursor.getString(idxTitle)
            val idxPublishedAt = cursor.getColumnIndex("published_at")
            publishedAt = cursor.getString(idxPublishedAt)
            val idxUrlToImage = cursor.getColumnIndex("url_to_image")
            urlToImage = cursor.getString(idxUrlToImage)
            val idxUrl = cursor.getColumnIndex("url")
            url = cursor.getString(idxUrl)
            articleList.add(Article(url, urlToImage, publishedAt, title))
        }
        val lvArticles = view.findViewById<ListView>(R.id.lvArticles)
        lvArticles.adapter = ArticleAdapter(activity!!, articleList)


        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        val helper = DataBaseHelper(activity!!)
        helper.close()
        super.onDestroy()
    }

}
