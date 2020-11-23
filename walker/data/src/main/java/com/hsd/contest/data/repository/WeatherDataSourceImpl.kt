package com.hsd.contest.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.ListProvinces
import com.hsd.contest.domain.entities.Municipality

class WeatherDataSourceImpl(private val weatherService: WeatherService) : WeatherDataSource {
    override suspend fun getProvinces(): Either<ErrorResponse, ListProvinces> {
        val response = weatherService.getProvinces()
        return if (response.isSuccessful) {
            response.body()!!.toDomain().right()
        } else {
            ErrorResponse(response.errorBody().toString()).left()
        }
    }

    override suspend fun getMunicipality(code: String): Either<ErrorResponse, ListMunicipality>{
        val response = weatherService.getMunicipality(code)
        return if (response.isSuccessful) {
            response.body()!!.toDomain().right()
        } else {
            ErrorResponse(response.errorBody().toString()).left()
        }
    }
}