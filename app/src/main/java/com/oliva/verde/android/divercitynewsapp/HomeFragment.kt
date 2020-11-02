package com.oliva.verde.android.divercitynewsapp

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding
import io.reactivex.disposables.CompositeDisposable


class HomeFragment : Fragment() {

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private lateinit var binding : NewsRowBinding
    private lateinit var articleAdapter: ArticleAdapter

    // CompositeDisposable : 複数の要素(API通信など)をまとめて格納・削除ができる
    var compositeDisposable = CompositeDisposable()
    // var mRealm = RealmHelper().mRealm
    var articleList = mutableListOf<Article>()
    var copiedArticleList = mutableListOf<Article>()
    var longClickedId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // オプションメニューの表示を有効にする
        setHasOptionsMenu(true)
        // Realm.init(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        RealmHelper().mRealm.close()
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_for_home_fragment, menu)
        val menuItem = menu.findItem(R.id.search_article)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchRequest(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return if (newText.isEmpty()) {
                    val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
                    val adapter = lvArticles?.adapter as ArticleAdapter // リサイクラービューに設定されているアダプターを取得
                    articleList.clear()
                    articleList.addAll(copiedArticleList)
                    adapter.notifyDataSetChanged()
                    true
                } else {
                    false
                }
            }
        })
    }


    fun searchRequest(text : String) {
        val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
        val adapter = lvArticles?.adapter as ArticleAdapter // リサイクラービューに設定されているアダプターを取得
        val filteredList = articleList.filter { it.title.contains(text) }
        articleList.clear()
        articleList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater = MenuInflater(activity)
        menuInflater.inflate(R.menu.context_menu_add_to_stock, menu)
        menu.setHeaderTitle(R.string.news_list_context_header)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // 長押しされたViewに関する情報を取得する
        val article = articleList[longClickedId]
        RealmHelper().create(article.url, article.urlToImage, article.publishedAt, article.title)
        Toast.makeText(activity, R.string.success_to_add_to_stock, Toast.LENGTH_LONG).show()
        return super.onContextItemSelected(item)
    }

    // 長押しされた記事のポジションを設定
    inner class ListItemLongClickListener(val position : Int) : View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            longClickedId = position
            return false
        }
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
}
