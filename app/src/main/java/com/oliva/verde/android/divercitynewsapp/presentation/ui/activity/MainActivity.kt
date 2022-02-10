package com.oliva.verde.android.divercitynewsapp.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.presentation.ui.Screen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
    }
    
    @Composable
    fun MainScreenView() {
        val navController = rememberNavController()
        val items = listOf(
            Screen.ArticleList,
            Screen.StockArticleList
        )
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->

                    }
                }
            }
        ) {
            
        }
    }

    @Composable
    fun BottomNavigationItem(
        screen: Screen,
        modifier: Modifier = Modifier
    ) {

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }
}
