package com.hsd.contest.data.repository

import com.hsd.contest.data.response.ListMunicipalityResponse
import com.hsd.contest.data.response.ListProvincesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("provincias")
    suspend fun getProvinces(): Response<ListProvincesResponse>

    @GET("provincias/{provinceID}/municipios")
    suspend fun getMunicipality(@Path("provinceID") code: String): Response<ListMunicipalityResponse>
}