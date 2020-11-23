package com.hsd.contest.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.Routes

class RoutesDataSourceImpl(private val services: RouteService) : RoutesDataSource {
    override suspend fun getRoutes(): Either<ErrorResponse, Routes> {
        val response = services.getRoutes()
        return if (response.isSuccessful) {
            response.body()!!.toDomain().right()
        } else {
            ErrorResponse(response.errorBody().toString()).left()
        }
    }
}