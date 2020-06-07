package com.oliva.verde.android.divercitynewsapp

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_row.view.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log


class HomeFragment : Fragment() {
    var articleList = mutableListOf<Article>()

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
        val lvArticles = view.findViewById<RecyclerView>(R.id.lvArticles)
        registerForContextMenu(lvArticles)
        val receiver = NewsInfoReceiver()
        receiver.execute()

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
        menuInflater.inflate(R.menu.context_menu_add_to_stock, menu)
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

    private inner class RecycleListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageRow : ImageView
        var titleRow : TextView
        var publishDateRow : TextView

        init {
            imageRow = itemView.findViewById(R.id.image_row)
            titleRow = itemView.findViewById(R.id.title_row)
            publishDateRow = itemView.findViewById(R.id.publish_date_row)
        }
    }

    private inner class RecycleListAdapter() : RecyclerView.Adapter<RecycleListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleListViewHolder {
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.news_row, parent, false)
            val holder = RecycleListViewHolder(view)
            return holder
        }

        override fun onBindViewHolder(holder: RecycleListViewHolder, position: Int) {
            val article = articleList[position]
            val title = article.title
            val publishedAt = article.publishedAt
            holder.titleRow.text = title
            holder.publishDateRow.text = publishedAt.substring(0..9)
            Picasso.get().load(article.urlToImage).into(holder.imageRow)
            holder.itemView.setOnClickListener(ListItemClickListener(position))
        }

        override fun getItemCount(): Int {
            return articleList.size
        }
    }

    private inner class ListItemClickListener(position : Int) : View.OnClickListener {
        val position = position
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
        /**
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item = parent?.getItemAtPosition(position) as Article // Articleクラスにキャスト
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
        */
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
            val lvArticles = view?.findViewById<RecyclerView>(R.id.lvArticles)
            val layout = LinearLayoutManager(activity)
            lvArticles?.layoutManager = layout
            // 独自定義のAdapterクラスをlayoutに紐づける
            lvArticles?.adapter = RecycleListAdapter()

            val decorator = DividerItemDecoration(activity, layout.orientation)
            lvArticles?.addItemDecoration(decorator)
            // lvArticles?.onItemClickListener = ListItemClickListener()
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

}
