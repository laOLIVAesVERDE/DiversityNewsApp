package com.oliva.verde.android.divercitynewsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.activity_top.*
//import androidx.browser.customtabs.CustomTabsIntent

class MainActivity : AppCompatActivity() {
    private val _helper = DataBaseHelper(this@MainActivity)
    var articleList = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        // findNavController : destination(各フラグメント)への移動を管理するnacControllerオブジェクトを取得する
        val navController = findNavController(R.id.nav_host_fragment)
        // ナビバーの動作によって、フラグメントの動作を制御する
        setupWithNavController(bottom_navigation, navController)
        /**
        val lvArticles = findViewById<ListView>(R.id.lvArticles)
        registerForContextMenu(lvArticles)
        val receiver = NewsInfoReceiver()
        receiver.execute()
        */
    }

    /**
    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
        menu.setHeaderTitle(R.string.news_list_context_header)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // 長押しされたViewに関する情報を取得する
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        // 長押しされたリストのポジションを取得
        val listPosition = info.position
        // ポジションに合致した記事データを取得
        val article = articleList[listPosition]
        // データベースに格納するデータを取得
        val url = article.url
        val urlToImage = article.urlToImage
        val publishedAt = article.publishedAt.substring(0, 10)
        val title = article.title

        // 以下、データベースへの保存処理
        val db = _helper.writableDatabase
        val sqlInsert = "INSERT INTO stocked_articles (url, url_to_image, published_at, title) VALUES (?, ?, ?, ?)"
        val stmt = db.compileStatement(sqlInsert)
        stmt.bindString(1, url)
        stmt.bindString(2, urlToImage)
        stmt.bindString(3, publishedAt)
        stmt.bindString(4, title)
        stmt.executeInsert()
        Toast.makeText(applicationContext, R.string.success_to_add_to_stock, Toast.LENGTH_LONG).show()
        return super.onContextItemSelected(item)
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item = parent?.getItemAtPosition(position) as Article // Articleクラスにキャスト
            // url文字列を取得
            val url = item.url
            /**
            //以下、Custom Tabs機能を使って記事の詳細を表示する
            // Custom Tabを表示するBuilderオブジェクトを取得
            val builder = CustomTabsIntent.Builder()
            // CustomTabsIntentオブジェクトを取得
            val customTabsIntent = builder.build()
            // Uriを指定し、Custom Tabを表示する
            customTabsIntent.launchUrl(this@MainActivity, Uri.parse(url))
            */
        }
    }

    private inner class NewsInfoReceiver() : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val apiKey = "413005df5f58476c868396878a752fb8"
            val searchWords = arrayOf("多様性推進", "企業")
            val urlStr = "http://newsapi.org/v2/everything?q=${searchWords[0]}+${searchWords[1]}&apiKey=${apiKey}"
            val url = URL(urlStr)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.connect()
            val stream = con.inputStream
            // JSON形式に変換
            val result = is2String(stream)
            con.disconnect()
            stream.close()
            return result
        }

        override fun onPostExecute(result: String?) {
            val rootJSON = JSONObject(result)
            val articleArray = rootJSON.getJSONArray("articles")
            var title = ""
            var publishedAt = ""
            var urlToImage = ""
            var url = ""
            var article : JSONObject
            for (i in 0..19) {
                article = articleArray.getJSONObject(i)
                title = article.getString("title")
                publishedAt = article.getString("publishedAt")
                urlToImage = article.getString("urlToImage")
                url = article.getString("url")
                articleList.add(Article(url, urlToImage, publishedAt, title))

            }
            val lvArticles = findViewById<ListView>(R.id.lvArticles)
            // 独自定義のAdapterクラスをlayoutに紐づける
            lvArticles.adapter = ArticleAdapter(this@MainActivity, articleList)
            lvArticles.onItemClickListener = ListItemClickListener()
        }

        private fun is2String(stream : InputStream) : String {
            val sb = StringBuilder()
            val reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
            var line = reader.readLine()
            while (line != null) {
                sb.append(line)
                line = reader.readLine()
            }
            reader.close()
            return sb.toString()
        }

    }
    */
}
