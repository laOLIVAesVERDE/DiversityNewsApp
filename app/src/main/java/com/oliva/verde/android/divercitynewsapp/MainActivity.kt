package com.oliva.verde.android.divercitynewsapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        // レシーバオブジェクト取得 & executeする
        val receiver = NewsInfoReceiver()
        receiver.execute()
        /**
        // Retrofit : API通信を行うためのライブラリ
        // Retrofitオブジェクトを取得
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build()
        // APIエンドポイント(APIにアクセスするためのURI)の生成
        val api = retrofit.create(ApiService::class.java)
        // エンドポイントにリクエスト
        api.getNews("413005df5f58476c868396878a752fb8", "jp").enqueue(object: Callback<ResponseData> {
            // 通信が失敗したときの処理
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {

            }
            // 通信成功したときの処理
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                findViewById<TextView>(R.id.textview).text = response.body().toString()
            }

        })
        */

    }

    private inner class NewsInfoReceiver() : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            val apiKey = "413005df5f58476c868396878a752fb8"
            val searchWords = arrayOf("多様性", "企業")
            val urlStr = "http://newsapi.org/v2/everything?q=${searchWords[0]}+${searchWords[1]}&apiKey=${apiKey}"
            val url = URL(urlStr)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.connect()
            val stream = con.inputStream
            val result = is2String(stream)
            con.disconnect()
            stream.close()
            return result
        }

        override fun onPostExecute(result: String?) {
            val rootJSON = JSONObject(result)
            val articles = rootJSON.getJSONArray("articles")
            val firstArticle = articles.getJSONObject(0)
            val title = firstArticle.getString("title")
            val tv = findViewById<TextView>(R.id.textview)
            tv.text = title

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
