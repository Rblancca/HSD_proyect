package com.hsd.contest.domain.usecase

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.ListProvinces
import com.hsd.contest.domain.entities.WeatherInfo

interface WeatherRepository {
    suspend fun getProvinces(): Either<ErrorResponse, ListProvinces>
    suspend fun getMunicipality(code: String): Either<ErrorResponse, ListMunicipality>
    suspend fun getWeather(code: String, position: String): Either<ErrorResponse, WeatherInfo>
}