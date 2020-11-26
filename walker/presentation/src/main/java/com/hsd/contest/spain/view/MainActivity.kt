package com.hsd.contest.spain.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hsd.contest.spain.databinding.SplashActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}