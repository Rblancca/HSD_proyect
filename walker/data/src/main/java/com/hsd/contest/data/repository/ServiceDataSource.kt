package com.hsd.contest.data.repository

import com.hsd.contest.data.response.RoutesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ServiceDataSource {
    @GET("api/datos/senderos.json")
    suspend fun getRoutes(): Response<RoutesResponse>
}