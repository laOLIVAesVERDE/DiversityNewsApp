package com.oliva.verde.android.divercitynewsapp

import android.content.Context
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

}