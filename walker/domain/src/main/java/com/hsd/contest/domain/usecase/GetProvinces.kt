package com.hsd.contest.domain.usecase

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListProvinces

class GetProvinces(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(): Either<ErrorResponse, ListProvinces> = weatherRepository.getProvinces()
}