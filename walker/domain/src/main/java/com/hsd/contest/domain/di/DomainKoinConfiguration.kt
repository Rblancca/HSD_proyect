package com.hsd.contest.domain.di

import com.hsd.contest.domain.usecase.GetMunicipality
import com.hsd.contest.domain.usecase.GetProvinces
import com.hsd.contest.domain.usecase.GetRoutes
import com.hsd.contest.domain.usecase.GetWeather
import org.koin.dsl.module

class DomainKoinConfiguration {
    fun getModule() = module {
        factory { GetRoutes(get()) }
        factory { GetProvinces(get()) }
        factory { GetMunicipality(get()) }
        factory { GetWeather(get()) }
    }
}