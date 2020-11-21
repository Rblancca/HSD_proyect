package com.hsd.contest.domain.usecase

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes

class GetRoutes(private val serviceRepository: ServiceRepository) {
    suspend operator fun invoke(): Either<ErrorResponse, Routes> = serviceRepository.getRoutes()
}