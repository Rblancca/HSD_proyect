package com.hsd.contest.domain.usecase

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.Municipality

class GetMunicipality (private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(code: String): Either<ErrorResponse, ListMunicipality> = weatherRepository.getMunicipality(code)
}