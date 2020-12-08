package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.WeatherInfo

data class WeatherResponse(

    @SerializedName("municipio") val municipality: MunicipalityResponse,
    @SerializedName("fecha") val date: String,
    @SerializedName("stateSky") val stateSky: StateSkyResponse,
    @SerializedName("temperatura_actual") val temperatureActual: Int,
    @SerializedName("temperaturas") val temperatures: TemperaturesResponse,
    @SerializedName("proximos_dias") val nextDays: List<NextDaysResponse>
) : Mappable<WeatherInfo> {
    override fun toDomain(): WeatherInfo =
        WeatherInfo(
            date,
            municipality.name,
            temperatureActual,
            temperatures.max,
            temperatures.min,
            stateSky.description,
            nextDays.map { it.toDomain() }
        )
}