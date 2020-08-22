package com.oliva.verde.android.divercitynewsapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*


class StockFragment : Fragment() {
    var articleList = mutableListOf<Article>()
    var filteredList = mutableListOf<Article>()
    var longClickedId = -1
    var filteringFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stock, container, false)
        // articleList = selectAllArticle()
        articleList = RealmHelper().read()
        Log.d("NewsApp", articleList.toString())
        // copiedArticleList = articleList.toMutableList()
        val lvArticles = view.findViewById<RecyclerView>(R.id.lvArticles)
        // LayoutManager : 各アイテムを表示形式を管理するクラス
        val layout = LinearLayoutManager(activity) // LinearLayoutManager : 各アイテムを縦のリストで表示する
        // リサイクラービューオブジェクトのLayoutManagerプロパティにLinearLayoutManagerを設定
        lvArticles.layoutManager = layout // 各アイテムが縦のリストで表示されるようになる
        // 独自定義のAdapterクラスをlayoutに紐づける
        lvArticles.adapter = RecycleListAdapter(this@StockFragment, articleList)
        // リサイクラービューに区切り線を追加
        val decorator = DividerItemDecoration(activity, layout.orientation)
        lvArticles?.addItemDecoration(decorator)
        return view
    }

    override fun onDestroy() {
        val helper = DataBaseHelper(requireActivity())
        helper.close()
        super.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_search_article, menu)
        val menuItem = menu.findItem(R.id.search_article)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchRequest(query)
                filteringFlag = 1
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return if (newText.isEmpty()) {
                    // Log.d("NewsApp", copiedArticleList.toString())
                    articleList = RealmHelper().read()
                    val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
                    lvArticles?.adapter = RecycleListAdapter(this@StockFragment, articleList)
                    filteringFlag = 0
                    true
                } else {
                    false
                }
            }
        })
    }

    fun searchRequest(text : String) {
        val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
        val adapter = lvArticles?.adapter as RecycleListAdapter // リサイクラービューに設定されているアダプターを取得
        filteredList = RealmHelper().search(text)
        lvArticles.adapter = RecycleListAdapter(this@StockFragment, filteredList)
        Log.d("NewsApp", filteredList.toString())
        adapter.notifyDataSetChanged()
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
        // 長押しされた記事オブジェクトをリサイクラービューから削除する
        val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
        var article : Article
        // 長押しされた記事オブジェクトをリストビューから取得
        if (filteringFlag == 0) {
            article = articleList[longClickedId]
        } else {
            article = filteredList[longClickedId]
        }
        Log.d("NewsApp", article.toString())
        RealmHelper().delete(article.id)
        Log.d("NewsApp", RealmHelper().read().toString())
        val adapter = lvArticles?.adapter as RecycleListAdapter // リサイクラービューに設定されているアダプターを取得
        // 記事オブジェクト配列から記事オブジェクトを削除
        // articleList.remove(article)
        // アダプターに、アダプト対象の記事オブジェクトの変更を知らせる
        adapter.notifyDataSetChanged()
        Toast.makeText(activity, R.string.success_to_remove_from_stock, Toast.LENGTH_LONG).show()

        return super.onContextItemSelected(item)
    }

    inner class ListItemClickListener(val position: Int) : View.OnClickListener {
        override fun onClick(view: View?) {
            val item = articleList[position]
            // url文字列を取得
            val url = item.url
            //以下、Custom Tabs機能を使って記事の詳細を表示する
            // Custom Tabを表示するBuilderオブジェクトを取得
            val builder = CustomTabsIntent.Builder()
            // CustomTabsIntentオブジェクトを取得
            val customTabsIntent = builder.build()
            // Uriを指定し、Custom Tabを表示する
            customTabsIntent.launchUrl(activity!!, Uri.parse(url))
        }
    }

    // 長押しされた記事のポジションを設定
    inner class ListItemLongClickListener(val position: Int) : View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            longClickedId = position
            Log.d("NewsApp", articleList[position].id)
            return false
        }
    }

    private fun selectAllArticle() : MutableList<Article> {
        val allStockedArticles = mutableListOf<Article>()

        var title = ""
        var publishedAt = ""
        var urlToImage = ""
        var url = ""
        Log.d("NewsApp", RealmHelper().read().toString())
        for (articleData in RealmHelper().read()) {
            url = articleData.url
            Log.d("NewsApp", "url : ${url}")
            urlToImage = articleData.urlToImage
            publishedAt = articleData.publishedAt
            title = articleData.title
            Log.d("NewsApp", Article(url, urlToImage, publishedAt, title).toString())
            allStockedArticles.add(Article(url, urlToImage, publishedAt, title))
        }

        /**
        val helper = DataBaseHelper(requireActivity())
        val db = helper.writableDatabase
        val sql = "SELECT * FROM stocked_articles"
        val cursor =  db.rawQuery(sql, null)
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
            allStockedArticles.add(Article(url, urlToImage, publishedAt, title))
        }
        */

        Log.d("NewsApp", allStockedArticles.toString())
        return allStockedArticles

    }
}
