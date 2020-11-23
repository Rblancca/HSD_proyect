package com.hsd.contest.data.repository

import arrow.core.Either
import com.hsd.contest.domain.entities.ErrorResponse
import com.hsd.contest.domain.entities.ListMunicipality
import com.hsd.contest.domain.entities.ListProvinces
import com.hsd.contest.domain.entities.Municipality

interface WeatherDataSource {
    suspend fun getProvinces(): Either<ErrorResponse, ListProvinces>
    suspend fun getMunicipality(code: String): Either<ErrorResponse, ListMunicipality>
}