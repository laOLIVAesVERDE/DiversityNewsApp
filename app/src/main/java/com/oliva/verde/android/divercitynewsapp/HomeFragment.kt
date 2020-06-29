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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    var articleList = mutableListOf<Article>()
    var longClickedId = -1
    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // オプションメニューの表示を有効にする
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val retrofit = Retrofit.Builder() // ビルダーオブジェクトを取得
                        // Calling baseUrl is required before calling build(). All other methods are optional.
                        // build前にbaseUrlが必要となる。他はオプション
                        .baseUrl("https://newsapi.org/") // baseurlを指定
                        .addConverterFactory(GsonConverterFactory.create()) // JsonオブジェクトをGsonに変換
                        .build() // Retrofitオブジェクトの生成

        // RetrofitオブジェクトにAPIサービスインスタンスによって定義されたAPIエンドポイントを実装する
        val api = retrofit.create(ApiService::class.java)
        // 検索クエリの指定
        val apiKey = "413005df5f58476c868396878a752fb8"
        val searchWord = "ダイバーシティ"
        // APIエンドポイントにリクエスト
        api.getNews(apiKey, searchWord).enqueue(object : Callback<ResponseData> { // enqueue : 非同期でリクエストを実行
            // 失敗時の処理
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.i("NewsApp", "onFailure")
                // ダイアログ表示させたい
            }
            // 成功時の処理
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val res = response.body() // ResponseData(articles=[Article(), Article(), ...]
                if (res != null) {
                    articleList = res.articles
                }
                val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
                // LayoutManager : 各アイテムを表示形式を管理するクラス
                val layout = LinearLayoutManager(activity) // LinearLayoutManager : 各アイテムを縦のリストで表示する
                // リサイクラービューオブジェクトのLayoutManagerプロパティにLinearLayoutManagerを設定
                lvArticles?.layoutManager = layout // 各アイテムが縦のリストで表示されるようになる
                // 独自定義のAdapterクラスをlayoutに紐づける
                lvArticles?.adapter = RecycleListAdapter(this@HomeFragment, articleList)
                // リサイクラービューに区切り線を追加
                val decorator = DividerItemDecoration(activity, layout.orientation)
                lvArticles?.addItemDecoration(decorator)
            }
        })
        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroy() {
        val helper = DataBaseHelper(activity!!)
        helper.close()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_search_article, menu)
        val menuItem = menu.findItem(R.id.search_article)
        val searchView = menuItem.actionView

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

        // データベースに格納するデータを取得
        val url = article.url
        val urlToImage = article.urlToImage
        val publishedAt = article.publishedAt.substring(0, 10)
        val title = article.title

        // 以下、データベースへの保存処理
        val helper = DataBaseHelper(activity!!)
        val db = helper.writableDatabase
        val sqlInsert = "INSERT INTO stocked_articles (url, url_to_image, published_at, title) VALUES (?, ?, ?, ?)"
        val stmt = db.compileStatement(sqlInsert)
        stmt.bindString(1, url)
        stmt.bindString(2, urlToImage)
        stmt.bindString(3, publishedAt)
        stmt.bindString(4, title)
        stmt.executeInsert()
        Toast.makeText(activity, R.string.success_to_add_to_stock, Toast.LENGTH_LONG).show()

        return super.onContextItemSelected(item)
    }

    inner class ListItemLongClickListener(position : Int) : View.OnLongClickListener {
        val pos = position
        override fun onLongClick(v: View?): Boolean {
            longClickedId = pos
            return false
        }
    }

    // 長押しされた記事のポジションを設定
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

    /**
    private inner class NewsInfoReceiver() : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val apiKey = "413005df5f58476c868396878a752fb8"
            val searchWords = arrayOf("LGBT", "取り組み")
            val urlStr = "http://newsapi.org/v2/everything?q=${searchWords[0]}+${searchWords[1]}&apiKey=${apiKey}"
            val url = URL(urlStr)
            // OkHttpクライアントオブジェクトを取得
            val client = OkHttpClient()
            // ビルダーオブジェクトを取得
            val bulider = okhttp3.Request.Builder()
            // リクエスト先URLを指定し、リクエストオブジェクトを取得
            val request = bulider.url(url).build()
            // newCall : クライアントオブジェクトを指定リクエストに対する呼び出し準備完了状態にする
            val call = client.newCall(request)
            // リクエストを実行
            val response = call.execute()
            // レスポンスをテキストとして取得する
            val body = response.body
            val result = body?.string()
            // クローズ処理
            body?.close()

            return result!!
        }

        override fun onPostExecute(result: String?) {
            // JSONオブジェクトから、記事のデータを取得
            val rootJSON = JSONObject(result)
            val articleArray = rootJSON.getJSONArray("articles")
            var title = ""
            var publishedAt = ""
            var urlToImage = ""
            var url = ""
            var article : JSONObject
            for (i in 0 until articleArray.length()) {
                article = articleArray.getJSONObject(i)
                title = article.getString("title")
                publishedAt = article.getString("publishedAt")
                urlToImage = article.getString("urlToImage")
                url = article.getString("url")
                articleList.add(Article(url, urlToImage, publishedAt, title))
            }
            val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
            // LayoutManager : 各アイテムを表示形式を管理するクラス
            val layout = LinearLayoutManager(activity) // LinearLayoutManager : 各アイテムを縦のリストで表示する
            // リサイクラービューオブジェクトのLayoutManagerプロパティにLinearLayoutManagerを設定
            lvArticles?.layoutManager = layout // 各アイテムが縦のリストで表示されるようになる
            // 独自定義のAdapterクラスをlayoutに紐づける
            lvArticles?.adapter = RecycleListAdapter(this@HomeFragment, articleList)
            // リサイクラービューに区切り線を追加
            val decorator = DividerItemDecoration(activity, layout.orientation)
            lvArticles?.addItemDecoration(decorator)
        }
    }
    */
}
