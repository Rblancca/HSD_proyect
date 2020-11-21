package com.hsd.contest.data.di

import com.hsd.contest.data.repository.RoutesDataSource
import com.hsd.contest.data.repository.RoutesDataSourceImpl
import com.hsd.contest.data.repository.ServiceRepositoryImpl
import com.hsd.contest.domain.usecase.ServiceRepository
import org.koin.dsl.module

class DataKoinConfiguration {
    fun getModule() = module {
        single<RoutesDataSource> { RoutesDataSourceImpl(get()) }
        single<ServiceRepository> { ServiceRepositoryImpl(get()) }
    }
}