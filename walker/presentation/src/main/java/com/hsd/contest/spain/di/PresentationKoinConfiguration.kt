package com.hsd.contest.spain.di

import com.hsd.contest.data.repository.RoutesRepositoryImpl
import com.hsd.contest.domain.usecase.GetWeather
import com.hsd.contest.domain.usecase.RoutesRepository
import com.hsd.contest.spain.view.home.HomeViewModel
import com.hsd.contest.spain.view.sportprofile.ProfileViewModel
import com.hsd.contest.spain.view.sportprofile.WalkingRepository
import com.hsd.contest.spain.view.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PresentationKoinConfiguration {
    fun getModule() = module {
        viewModel { HomeViewModel(get()) }
        viewModel { WeatherViewModel(get(), get(), get()) }
        viewModel { ProfileViewModel(get()) }
    }
}