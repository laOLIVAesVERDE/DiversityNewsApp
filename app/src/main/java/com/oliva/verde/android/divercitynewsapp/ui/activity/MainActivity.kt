package com.oliva.verde.android.divercitynewsapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.ui.fargment.HomeFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        /*
        // findNavController : NavHostに表示するコンテンツを、NavigationGraphを元に切り替える処理をするNavControllerを取得し、画面を遷移させる。
        // NavHostはNavigationGraphの宛先を表示するための空のコンテナのこと。
        val navController = findNavController(R.id.nav_host_fragment)
        // NavControllerに、NavControllerとともに用いるBottomNavigationをセットする
        // そのため、BottomNavigationViewの各menuのidと、navigation_graphの各fragmentのidは対応している必要がある
        setupWithNavController(bottom_navigation, navController)

         */

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setLogo(R.drawable.ic_toolbar_icon)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.nav_host_fragment,
                HomeFragment()
            )
            .commit()
    }
}
