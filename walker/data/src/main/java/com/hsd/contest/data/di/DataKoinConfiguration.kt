package com.hsd.contest.data.di

import com.hsd.contest.data.repository.*
import com.hsd.contest.domain.usecase.RoutesRepository
import com.hsd.contest.domain.usecase.WeatherRepository
import org.koin.dsl.module

class DataKoinConfiguration {
    fun getModule() = module {
        single<RoutesDataSource> { RoutesDataSourceImpl(get()) }
        single<WeatherDataSource> { WeatherDataSourceImpl(get()) }
        single<RoutesRepository> { RoutesRepositoryImpl(get()) }
        single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    }
}