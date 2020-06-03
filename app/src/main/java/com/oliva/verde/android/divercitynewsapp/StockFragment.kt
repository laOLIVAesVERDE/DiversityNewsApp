package com.oliva.verde.android.divercitynewsapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import java.util.zip.Inflater

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
        lvArticles?.adapter = ArticleAdapter(activity!!, articleList)

        //val lvArticles = view.findViewById<ListView>(R.id.lvArticles)
        lvArticles.onItemClickListener = ListItemClickListener()
        registerForContextMenu(lvArticles)

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        val helper = DataBaseHelper(activity!!)
        helper.close()
        super.onDestroy()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater = MenuInflater(activity)
        menuInflater.inflate(R.menu.context_menu_remove_from_stock, menu)
        menu.setHeaderTitle(R.string.news_list_context_header)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val helper = DataBaseHelper(activity!!)
        val db = helper.writableDatabase
        val sqlSelectAll = "SELECT * FROM stocked_articles"
        var cursor = db.rawQuery(sqlSelectAll, null)
        val idArray = arrayListOf<Long>()
        while(cursor.moveToNext()) {
            val idxId = cursor.getColumnIndex("_id")
            idArray.add(cursor.getLong(idxId))
        }

        Log.i("NewsApp_1", cursor.count.toString())
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val selectedArticleId = idArray[position]
        // Log.i("NewsApp", selectedArticleId.toString())
        val sqlDelete = "DELETE FROM stocked_articles WHERE _id = ?"
        val stmt = db.compileStatement(sqlDelete)
        stmt.bindLong(1, selectedArticleId)
        stmt.executeUpdateDelete()

        val lvArticles = view?.findViewById<ListView>(R.id.lvArticles)
        val article = lvArticles?.getItemAtPosition(position) as Article
        val adapter = lvArticles?.adapter as ArticleAdapter
        articleList.remove(article)
        adapter.notifyDataSetChanged()


        Toast.makeText(activity, R.string.news_list_context_remove, Toast.LENGTH_LONG).show()

        return super.onContextItemSelected(item)
    }

    private fun selectAllStockArticle(listView: ListView?) {
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
        val adapter = ArticleAdapter(activity!!, articleList)
        adapter.notifyDataSetChanged()
        Log.i("NewsApp", cursor.count.toString())
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item = parent?.getItemAtPosition(position) as Article
            val url = item.url
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(activity!!, Uri.parse(url))
        }
    }

}
