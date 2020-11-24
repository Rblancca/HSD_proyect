package com.hsd.contest.data.repository

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.ListProvinces
import com.hsd.contest.domain.entities.WeatherInfo
import com.hsd.contest.domain.usecase.WeatherRepository

class WeatherRepositoryImpl(private val weatherDataSource: WeatherDataSource) : WeatherRepository {
    override suspend fun getProvinces(): Either<ErrorResponse, ListProvinces> =
        weatherDataSource.getProvinces()

    override suspend fun getMunicipality(code: String): Either<ErrorResponse, ListMunicipality> =
        weatherDataSource.getMunicipality(code)

    override suspend fun getWeather(
        code: String,
        position: String
    ): Either<ErrorResponse, WeatherInfo> = weatherDataSource.getWeather(code, position)

}