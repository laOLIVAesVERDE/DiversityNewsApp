package com.oliva.verde.android.divercitynewsapp

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


class StockFragment : Fragment() {
    var articleList = mutableListOf<Article>()
    var filteredList = mutableListOf<Article>()
    var longClickedId = -1
    var searchFilteringFlag = 0
    var isReadFilteringFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stock, container, false)
        articleList = RealmHelper().read()
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

    override fun onResume() {
        super.onResume()
        if (isReadFilteringFlag == 1) {
            filteredList = RealmHelper().readIsNotRead()
            val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
            val adapter = lvArticles?.adapter as RecycleListAdapter // リサイクラービューに設定されているアダプターを取得
            lvArticles.adapter = RecycleListAdapter(this@StockFragment, filteredList)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        RealmHelper().mRealm.close()
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
                searchFilteringFlag = 1
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
                return if (newText.isEmpty() && isReadFilteringFlag == 0) {
                    articleList = RealmHelper().read()
                    lvArticles?.adapter = RecycleListAdapter(this@StockFragment, articleList)
                    searchFilteringFlag = 0
                    true
                } else if(newText.isEmpty() && isReadFilteringFlag == 1) {
                    filteredList = RealmHelper().readIsNotRead()
                    lvArticles?.adapter = RecycleListAdapter(this@StockFragment, filteredList)
                    searchFilteringFlag = 0
                    true
                } else {
                    false
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
        val adapter = lvArticles?.adapter as RecycleListAdapter
        when(item.itemId) {
            R.id.is_read -> {
                isReadFilteringFlag = 1
                filteredList = RealmHelper().readIsNotRead()
                lvArticles.adapter = RecycleListAdapter(this@StockFragment, filteredList)
            }
            R.id.all_article -> {
                isReadFilteringFlag = 0
                articleList = RealmHelper().read()
                lvArticles.adapter = RecycleListAdapter(this@StockFragment, articleList)
            }
        }
        adapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

    fun searchRequest(text : String) {
        val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
        val adapter = lvArticles?.adapter as RecycleListAdapter // リサイクラービューに設定されているアダプターを取得
        filteredList = RealmHelper().search(text)
        lvArticles.adapter = RecycleListAdapter(this@StockFragment, filteredList)
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
        // 長押しされた記事オブジェクトを取得
        val article : Article = if (searchFilteringFlag == 1 || isReadFilteringFlag == 1) {
            filteredList[longClickedId]

        } else {
            articleList[longClickedId]
        }
        // 記事DBから記事オブジェクトを削除
        RealmHelper().delete(article.id)
        val adapter = lvArticles?.adapter as RecycleListAdapter // リサイクラービューに設定されているアダプターを取得
        // アダプターに、アダプト対象の記事オブジェクトの変更を知らせる
        adapter.notifyDataSetChanged()
        Toast.makeText(activity, R.string.success_to_remove_from_stock, Toast.LENGTH_LONG).show()

        return super.onContextItemSelected(item)
    }

    inner class ListItemClickListener(val position: Int) : View.OnClickListener {
        override fun onClick(view: View?) {
            val article : Article = if (searchFilteringFlag == 1 || isReadFilteringFlag == 1) {
                filteredList[position]
            } else {
                articleList[position]
            }
            // url文字列を取得
            val url = article.url
            RealmHelper().updateFlag(article.id)
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
            return false
        }
    }

}
