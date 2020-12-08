package com.hsd.contest.spain.di

import com.hsd.contest.spain.view.home.HomeViewModel
import com.hsd.contest.spain.view.sportprofile.ProfileViewModel
import com.hsd.contest.spain.view.sportprofile.WalkingRepository
import com.hsd.contest.spain.view.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PresentationKoinConfiguration {
    fun getModule() = module {
        factory { WalkingRepository(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { WeatherViewModel(get(), get(), get()) }
        viewModel { ProfileViewModel(get(), get()) }
    }
}