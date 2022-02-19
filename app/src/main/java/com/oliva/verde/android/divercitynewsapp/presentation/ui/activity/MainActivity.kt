package com.oliva.verde.android.divercitynewsapp.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oliva.verde.android.divercitynewsapp.presentation.article_list.components.ArticleListScreen
import com.oliva.verde.android.divercitynewsapp.presentation.stock_article.components.StockArticleListScreen
import com.oliva.verde.android.divercitynewsapp.presentation.ui.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
            NavHost(navController = navController, startDestination = Screen.ArticleList.route) {
                composable(Screen.ArticleList.route) {
                    ArticleListScreen(navController = navController)
                }
                composable(Screen.StockArticleList.route) {
                    StockArticleListScreen(navController = navController)
                }
            }
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
                        navController.navigate(screen.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
