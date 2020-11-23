package com.hsd.contest.spain.di

import com.hsd.contest.spain.view.home.HomeViewModel
import com.hsd.contest.spain.view.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PresentationKoinConfiguration {
    fun getModule() = module {
        viewModel { HomeViewModel(get()) }
        viewModel { WeatherViewModel(get(), get()) }
    }
}