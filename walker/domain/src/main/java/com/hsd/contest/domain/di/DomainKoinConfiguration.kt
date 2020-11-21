package com.hsd.contest.domain.di

import com.hsd.contest.domain.usecase.GetRoutes
import org.koin.dsl.module

class DomainKoinConfiguration {
    fun getModule() = module {
        factory { GetRoutes(get()) }
    }
}