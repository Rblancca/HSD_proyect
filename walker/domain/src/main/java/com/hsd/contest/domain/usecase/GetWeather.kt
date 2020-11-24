package com.hsd.contest.domain.usecase

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.WeatherInfo

class GetWeather (private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(code: String, position: String): Either<ErrorResponse, WeatherInfo> = weatherRepository.getWeather(code, position)
}