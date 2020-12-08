package com.hsd.contest.domain.usecase

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes

interface RoutesRepository {
    suspend fun getRoutes(): Either<ErrorResponse, Routes>
}