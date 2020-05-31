package com.oliva.verde.android.divercitynewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StockDisplayActivity : AppCompatActivity() {
    val _helper = DataBaseHelper(this@StockDisplayActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_display)
    }
}
