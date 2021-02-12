package com.oliva.verde.android.divercitynewsapp.view.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.view.ui.fragment.HomeFragment
import com.oliva.verde.android.divercitynewsapp.view.ui.fragment.StockFragment
import kotlinx.android.synthetic.main.activity_top.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)

        val homeFragment = HomeFragment()
        val stockFragment = StockFragment()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setLogo(R.drawable.ic_toolbar_icon)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // setCurrentFragment(homeFragment)

        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        /*
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navi_home -> {
                    setCurrentFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navi_bookmark -> {
                    setCurrentFragment(stockFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

         */

        // findNavController : NavHostに表示するコンテンツを、NavigationGraphを元に切り替える処理をするNavControllerを取得し、画面を遷移させる。
        // NavHostはNavigationGraphの宛先を表示するための空のコンテナのこと。
        // val navController = findNavController(R.id.nav_host_fragment)
        // NavControllerに、NavControllerとともに用いるBottomNavigationをセットする
        // そのため、BottomNavigationViewの各menuのidと、navigation_graphの各fragmentのidは対応している必要がある
        // setupWithNavController(bottomNavigationView, navController)

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }
}
