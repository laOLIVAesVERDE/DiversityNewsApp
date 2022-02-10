package com.oliva.verde.android.divercitynewsapp.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import com.oliva.verde.android.divercitynewsapp.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)


    }
    
    @Composable
    fun MainScreenView() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation() {
                    
                }
            }
        ) {
            
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }
}
