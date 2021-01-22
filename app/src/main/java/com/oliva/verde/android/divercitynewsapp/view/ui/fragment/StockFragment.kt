package com.oliva.verde.android.divercitynewsapp.view.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.databinding.FragmentStockBinding
import com.oliva.verde.android.divercitynewsapp.view.adapter.ArticleAdapter
import com.oliva.verde.android.divercitynewsapp.view.callback.OnItemClickCallback
import com.oliva.verde.android.divercitynewsapp.viewmodel.StockFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StockFragment : Fragment() {
    companion object {
        val LOGTAG = "StockFragment"
    }
    var articleList = mutableListOf<Article>()
    var filteredList = mutableListOf<Article>()
    var longClickedId = -1
    var searchFilteringFlag = 0
    var isReadFilteringFlag = 0

    private val stockFragmentViewModel by lazy {
        ViewModelProvider(this).get(StockFragmentViewModel::class.java)
    }

    private lateinit var binding : FragmentStockBinding

    private val stockArticleAdapter : ArticleAdapter =
        ArticleAdapter(object : OnItemClickCallback {
            override fun onItemClick(article: Article) {
                article as Article.StockArticle
                val url = article.url
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(activity!!, Uri.parse(url))
            }

            override fun onContextClick(article: Article) {
                article as Article.StockArticle
                val button = view?.findViewById<ImageButton>(R.id.image_button)
                // val button = ArticleAdapter.BindingHolder(NewsRowBinding()).binding.imageButton
                val popupMenu  = PopupMenu(activity, button)
                popupMenu.menuInflater.inflate(R.menu.context_menu_remove_from_stock, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete_from_stock -> {
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    val targetStockArticle = StockArticle(
                                        id = 0,
                                        url = article.url,
                                        urlToImage = article.urlToImage,
                                        publishedAt = article.publishedAt,
                                        title = article.title,
                                        isReadFlag = false
                                    )
                                    stockFragmentViewModel.deleteTargetArticle(targetStockArticle)
                                }
                            }
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        })
        /*
        StockArticleAdapter(object : OnStockArticleClickCallBack {
            override fun onItemClick(stockArticle: StockArticle) {
                val url = stockArticle.url
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(activity!!, Uri.parse(url))
            }

            override fun onContextClick(article: Article) {
                val button = view?.findViewById<ImageButton>(R.id.image_button)
                // val button = ArticleAdapter.BindingHolder(NewsRowBinding()).binding.imageButton
                val popupMenu  = PopupMenu(activity, button)
                popupMenu.menuInflater.inflate(R.menu.context_menu_remove_from_stock, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_to_stock -> {
                            // CoroutineScope(Dispatchers.IO).launch { stockFragmentViewModel.deleteTargetArticle(article) }
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        })

         */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock, container, false)
        binding.apply {
            rvArticles.adapter = stockArticleAdapter
            rvArticles.addItemDecoration(DividerItemDecoration(rvArticles.context, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(LOGTAG, "onActivityCreated")
        stockFragmentViewModel.stockArticleListLiveData.observe(viewLifecycleOwner, Observer { stockArticles ->
            stockArticles.forEach {
                Log.d(LOGTAG, it.title)
            }
            stockArticleAdapter.setArticleList(stockArticles)
            getAllStockedArticles()
        })
    }

    private fun getAllStockedArticles() {
        CoroutineScope(Dispatchers.IO).launch {
            stockFragmentViewModel.getAllStockedArticles()
        }
    }

    /**
    // CustomTabsIntentで記事を閲覧した後、未読記事表示中であればリストを更新
    override fun onResume() {
        super.onResume()
        if (isReadFilteringFlag == 1) {
            // 再度、未読記事をリサイクラービューにセットする
            filteredList = RealmHelper().readIsNotRead()
            val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
            val adapter = lvArticles?.adapter as ArticleAdapter // リサイクラービューに設定されているアダプターを取得
            lvArticles.adapter = ArticleAdapter(this@StockFragment, filteredList)
            adapter.notifyDataSetChanged()
        }
    }
    **/


    override fun onDestroy() {
        // StockArticleDao.realm.close()
        super.onDestroy()
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_for_stock_fragment, menu)
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
                // 検索ワードがブランクかつ全ての記事を表示中
                return if (newText.isEmpty() && isReadFilteringFlag == 0) {
                    articleList = RealmHelper().read()
                    lvArticles?.adapter = ArticleAdapter(this@StockFragment, articleList)
                    searchFilteringFlag = 0
                    true
                // 検索ワードがブランクかつ未読記事を表示中
                } else if(newText.isEmpty() && isReadFilteringFlag == 1) {
                    filteredList = RealmHelper().readIsNotRead()
                    lvArticles?.adapter = ArticleAdapter(this@StockFragment, filteredList)
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
        val adapter = lvArticles?.adapter as ArticleAdapter
        when(item.itemId) {
            R.id.is_read -> {
                isReadFilteringFlag = 1
                filteredList = RealmHelper().readIsNotRead()
                lvArticles.adapter = ArticleAdapter(this@StockFragment, filteredList)
            }
            R.id.all_article -> {
                isReadFilteringFlag = 0
                articleList = RealmHelper().read()
                lvArticles.adapter = ArticleAdapter(this@StockFragment, articleList)
            }
        }
        adapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }

    fun searchRequest(text : String) {
        val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
        val adapter = lvArticles?.adapter as ArticleAdapter
        // 未読記事表示のフラグによって、DBから読み出す記事を変える
        filteredList = if (isReadFilteringFlag == 1) {
            RealmHelper().searchFromIsNotRead(text)
        } else {
            RealmHelper().search(text)
        }
        lvArticles.adapter = ArticleAdapter(this@StockFragment, filteredList)
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
        // 検索中または未読記事表示中であれば、フィルター済みの記事リストより記事データを取得する
        val article : Article = if (searchFilteringFlag == 1 || isReadFilteringFlag == 1) {
            filteredList[longClickedId]
        } else {
            articleList[longClickedId]
        }
        // 記事DBから記事オブジェクトを削除
        RealmHelper().delete(article.id)
        val adapter = lvArticles?.adapter as ArticleAdapter // リサイクラービューに設定されているアダプターを取得
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
    */
}
