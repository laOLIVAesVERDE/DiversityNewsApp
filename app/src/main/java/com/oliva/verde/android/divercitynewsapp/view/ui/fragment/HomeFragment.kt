package com.oliva.verde.android.divercitynewsapp.view.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oliva.verde.android.divercitynewsapp.service.model.Article
import com.oliva.verde.android.divercitynewsapp.viewmodel.HomeFragmentViewModel
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.databinding.FragmentHomeBinding
import com.oliva.verde.android.divercitynewsapp.service.model.StockArticle
import com.oliva.verde.android.divercitynewsapp.service.repository.database.StockArticleDao
import com.oliva.verde.android.divercitynewsapp.view.adapter.ArticleAdapter
import com.oliva.verde.android.divercitynewsapp.view.callback.OnItemClickCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class HomeFragment : Fragment() {
    companion object {
        val LOG_TAG = "HomeFragmnet"
    }

    private val homeFragmentViewModel by lazy {
        ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

    private lateinit var binding : FragmentHomeBinding

    private val articleAdapter : ArticleAdapter =
        ArticleAdapter(object : OnItemClickCallback {
            override fun onItemClick(article: Article) {
                article as Article.ResponseArticle
                val url = article.url
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(activity!!, Uri.parse(url))
            }

            override fun onContextClick(article: Article) {
                article as Article.ResponseArticle
                val button = view?.findViewById<ImageButton>(R.id.image_button)
                // val button = ArticleAdapter.BindingHolder(NewsRowBinding()).binding.imageButton
                val popupMenu  = PopupMenu(activity, button)
                popupMenu.menuInflater.inflate(R.menu.context_menu_add_to_stock, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_to_stock -> {
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
                                    homeFragmentViewModel.insertTargetArticle(targetStockArticle)
                                }
                            }
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        })

    var copiedArticleList = mutableListOf<Article>()
    var longClickedId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ConfirmOnCreate", "OnCreate")
        // オプションメニューの表示を有効にする
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "OnCreateView")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            rvArticles.adapter = articleAdapter
            rvArticles.addItemDecoration(DividerItemDecoration(rvArticles.context, LinearLayoutManager.VERTICAL))
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeFragmentViewModel.articleListLiveData.observe(viewLifecycleOwner, Observer { articles ->
            articles.let {
                articleAdapter.setArticleList(it)
            }
        })
    }

    override fun onDestroy() {
        // StockArticleDao.realm.close()
        super.onDestroy()
    }


    /**
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_for_home_fragment, menu)
        val menuItem = menu.findItem(R.id.search_article)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // searchRequest(query)
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
    */

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

    /*

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
    */
}
