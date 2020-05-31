package com.oliva.verde.android.divercitynewsapp

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.widget.ListView
import androidx.browser.customtabs.CustomTabsIntent
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    var articleList = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val lvArticles = view.findViewById<ListView>(R.id.lvArticles)
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


    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
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
            val lvArticles = view?.findViewById<ListView>(R.id.lvArticles)
            // 独自定義のAdapterクラスをlayoutに紐づける
            val activity = activity as Context
            lvArticles?.adapter = ArticleAdapter(activity, articleList)
            lvArticles?.onItemClickListener = ListItemClickListener()
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
