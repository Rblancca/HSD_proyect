package com.hsd.contest.spain.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hsd.contest.spain.R
import com.hsd.contest.spain.component.bottomview.BottomNavigationConfigurationModel
import com.hsd.contest.spain.component.bottomview.BottomNavigationDefaultModel
import com.hsd.contest.spain.component.bottomview.BottomNavigationManager
import com.hsd.contest.spain.component.bottomview.MyBottomNavigationView
import com.hsd.contest.spain.databinding.MenuActivityBinding
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools


class MenuActivity : AppCompatActivity() {
    var instance: HiAnalyticsInstance? = null

    private lateinit var binding: MenuActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HiAnalyticsTools.enableLog()
        instance = HiAnalytics.getInstance(this)
        binding = MenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            bottomNavigationManager.init()
        }
        bottomNavigationManager.initListeners()


        verifyPermissions()

    }

    private fun verifyPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                requestPermissions(strings, 1)
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                )
                requestPermissions(strings, 2)
            }
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "onRequestPermissionsResult: apply LOCATION PERMISSION successful")
            } else {
                Log.i("TAG", "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed")
            }
        }
        if (requestCode == 2) {
            if (grantResults.size > 2 && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i(
                    "TAG",
                    "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful"
                )
            } else {
                Log.i("TAG", "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed")
            }
        }
    }


}