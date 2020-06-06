package com.oliva.verde.android.divercitynewsapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.activity_top.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        // findNavController : NavHostに表示するコンテンツを、NavigationGraphを元に切り替える処理をするNavControllerを取得し、画面を遷移させる。
        // NavHostはNavigationGraphの宛先を表示するための空のコンテナのこと。
        val navController = findNavController(R.id.nav_host_fragment)
        // NavControllerに、NavControllerとともに用いるBottomNavigationをセットする
        // そのため、BottomNavigationViewの各menuのidと、navigation_graphの各fragmentのidは対応している必要がある
        setupWithNavController(bottom_navigation, navController)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.toolbar_title)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
    }
}
