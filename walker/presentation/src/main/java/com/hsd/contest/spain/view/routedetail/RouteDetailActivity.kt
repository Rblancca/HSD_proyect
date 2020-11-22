package com.hsd.contest.spain.view.routedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.hsd.contest.spain.databinding.RouteDetailActivityBinding

class RouteDetailActivity : AppCompatActivity() {
    val args: RouteDetailActivityArgs by navArgs()
    private lateinit var binding: RouteDetailActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RouteDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}