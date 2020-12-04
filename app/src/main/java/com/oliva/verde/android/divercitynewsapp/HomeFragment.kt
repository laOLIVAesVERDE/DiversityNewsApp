package com.oliva.verde.android.divercitynewsapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oliva.verde.android.divercitynewsapp.databinding.FragmentHomeBinding
import com.oliva.verde.android.divercitynewsapp.databinding.NewsRowBinding
import com.oliva.verde.android.divercitynewsapp.injection.DaggerApiComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class HomeFragment : Fragment() {
    @Inject
    lateinit var repository: Repository
    private var articleList = mutableListOf<Article>()

    private val homeFragmentViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(repository)
        ).get(HomeFragmentViewModel::class.java)

    }

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var binding : FragmentHomeBinding

    var copiedArticleList = mutableListOf<Article>()
    var longClickedId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ConfirmOnCreate", "OnCreate")
        // オプションメニューの表示を有効にする
        setHasOptionsMenu(true)
        // Realm.init(activity)
        DaggerApiComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ConfirmOnCreateView", "OnCreateView")

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val apiKey = "413005df5f58476c868396878a752fb8"
        val searchWord = "ダイバーシティ"

        homeFragmentViewModel.getArticles(apiKey, searchWord)
        /*
        homeFragmentViewModel.articles.observe(viewLifecycleOwner, Observer {
            it.forEach{ article ->
                articleList.add(article)
            }
            Log.d("ConfirmArticleList", articleList.toString())
            binding.rvArticles.adapter = ArticleAdapter(articleList)
            binding.rvArticles.layoutManager = LinearLayoutManager(this.context)
            ArticleAdapter(articleList).notifyDataSetChanged()
        })

         */

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel(homeFragmentViewModel)
    }

    override fun onDestroy() {
        // RealmHelper().mRealm.close()
        super.onDestroy()
    }

    private fun observeViewModel(viewModel: HomeFragmentViewModel) {
        viewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            if (articles != null) {

            }
        })
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
    */
}
