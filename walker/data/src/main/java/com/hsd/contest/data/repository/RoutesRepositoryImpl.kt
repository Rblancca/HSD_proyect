package com.hsd.contest.data.repository

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes
import com.hsd.contest.domain.usecase.RoutesRepository

class RoutesRepositoryImpl(private val serviceDataSource: RoutesDataSource) : RoutesRepository {
    override suspend fun getRoutes(): Either<ErrorResponse, Routes> =
        serviceDataSource.getRoutes()
}