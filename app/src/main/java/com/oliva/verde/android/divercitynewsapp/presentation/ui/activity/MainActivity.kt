package com.oliva.verde.android.divercitynewsapp.presentation.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oliva.verde.android.divercitynewsapp.R
import com.oliva.verde.android.divercitynewsapp.presentation.ui.Screen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreenView() }
    }
    
    @Composable
    fun MainScreenView() {
        val navController = rememberNavController()
        val screens = listOf(
            Screen.ArticleList,
            Screen.StockArticleList
        )
        Scaffold(
            bottomBar = {
                BottomNav(
                    navController = navController,
                    screenList = screens
                )
            }
        ) {
            
        }
    }

    @Composable
    fun BottomNav(
        navController: NavController,
        screenList: List<Screen>,
        modifier: Modifier = Modifier
    ) {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screenList.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = screen.icon, contentDescription = screen.route)
                    },
                    selected = currentRoute == screen.route,
                    onClick = {

                    }
                )
            }
        }
    }
}
