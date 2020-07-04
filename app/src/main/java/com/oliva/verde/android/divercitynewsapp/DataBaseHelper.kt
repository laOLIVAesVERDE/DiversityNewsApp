package com.oliva.verde.android.divercitynewsapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.StringBuilder

// 第一引数 : このヘルパークラスを扱うコンテキスト
class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "stocked_article.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sb = StringBuilder()
        sb.append("CREATE TABLE stocked_articles(")
        sb.append("_id INTEGER PRIMARY KEY,")
        sb.append("url TEXT,")
        sb.append("url_to_image TEXT,")
        sb.append("published_at TEXT,")
        sb.append("title TEXT")
        sb.append(")")
        val sql = sb.toString()

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    // 渡された記事をデータベースに保存する
    fun InsertArticle(article : Article) {
        val db = this.writableDatabase
        val sqlInsert = "INSERT INTO stocked_articles (url, url_to_image, published_at, title) VALUES (?, ?, ?, ?)"
        val stmt = db.compileStatement(sqlInsert)
        stmt.bindString(1, article.url)
        stmt.bindString(2, article.urlToImage)
        stmt.bindString(3, article.publishedAt)
        stmt.bindString(4, article.title)
        stmt.executeInsert()
    }

}