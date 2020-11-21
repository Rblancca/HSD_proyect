package com.hsd.contest.data.repository

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes
import com.hsd.contest.domain.usecase.ServiceRepository

class ServiceRepositoryImpl(private val routesDataSource: RoutesDataSource) : ServiceRepository {
    override suspend fun getRoutes(): Either<ErrorResponse, Routes> =
        routesDataSource.getRoutes()
}