package com.hsd.contest.data.repository

import arrow.core.Either
import com.hsd.contest.domain.entities.*

interface WeatherDataSource {
    suspend fun getProvinces(): Either<ErrorResponse, ListProvinces>
    suspend fun getMunicipality(code: String): Either<ErrorResponse, ListMunicipality>
    suspend fun getWeather(code: String, position: String): Either<ErrorResponse, WeatherInfo>
}