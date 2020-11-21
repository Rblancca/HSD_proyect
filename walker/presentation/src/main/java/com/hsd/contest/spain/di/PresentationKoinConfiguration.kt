package com.hsd.contest.spain.di

import com.hsd.contest.spain.view.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PresentationKoinConfiguration {
    fun getModule() = module {
        viewModel { HomeViewModel(get()) }
    }
}