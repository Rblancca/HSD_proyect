package com.hsd.contest.data.repository

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes

interface RoutesDataSource {
    suspend fun getRoutes(): Either<ErrorResponse, Routes>
}