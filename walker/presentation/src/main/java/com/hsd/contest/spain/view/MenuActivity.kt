package com.hsd.contest.spain.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hsd.contest.spain.R
import com.hsd.contest.spain.component.bottomview.BottomNavigationConfigurationModel
import com.hsd.contest.spain.component.bottomview.BottomNavigationDefaultModel
import com.hsd.contest.spain.component.bottomview.BottomNavigationManager
import com.hsd.contest.spain.component.bottomview.MyBottomNavigationView
import com.hsd.contest.spain.databinding.MenuActivityBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: MenuActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            bottomNavigationManager.init()
        }
        bottomNavigationManager.initListeners()
    }

    private val bottomNavigationManager: BottomNavigationManager by lazy {
        BottomNavigationManager(
            defaultModel = BottomNavigationDefaultModel(
                bottomIdGraphId = mapOf(
                    MyBottomNavigationView.Selected.NONE to R.navigation.home_navigation,
                    MyBottomNavigationView.Selected.START to R.navigation.weather_navigation,
                    MyBottomNavigationView.Selected.END to R.navigation.profile_navigation,
                ),
                mainBottomId = MyBottomNavigationView.Selected.NONE,
                containerId = binding.fcvActivityMenu.id,
                fragmentManager = supportFragmentManager,
                bottomNavigation = binding.mbnvActivityMenu,
                centerButton = binding.fabActivityMenu
            ),
            configurationModel = BottomNavigationConfigurationModel(
                keepWhenSelect = true,
                resetWhenReselect = true,
                goMainWhenBackPressed = true
            )
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mbnvActivityMenu.onSaveInstanceState(outState = outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.mbnvActivityMenu.onRestoreInstanceState(savedInstanceState = savedInstanceState)
        bottomNavigationManager.restore()
    }

    override fun onBackPressed() {
        if (!bottomNavigationManager.backPressed()) {
            super.onBackPressed()
        }
    }
}