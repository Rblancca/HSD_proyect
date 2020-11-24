package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.WeatherInfo
import com.hsd.contest.domain.entities.WeatherNextDays

data class WeatherResponse(

    @SerializedName("municipio") val municipio: MunicipalityResponse,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("stateSky") val stateSky: StateSky,
    @SerializedName("temperatura_actual") val temperatura_actual: Int,
    @SerializedName("temperaturas") val temperaturas: Temperaturas,
    //@SerializedName("humedad") val humedad: Int,
    //@SerializedName("viento") val viento: Int,
    //@SerializedName("lluvia") val lluvia: Int,
    //@SerializedName("imagen") val imagen: String,
    //@SerializedName("pronostico") val pronostico: PredictionResponse,
    @SerializedName("proximos_dias") val proximos_dias: List<NextDaysResponse>
   // @SerializedName("keywords") val keywordswords: String
) : Mappable<WeatherInfo> {
    override fun toDomain(): WeatherInfo =
        WeatherInfo(
            fecha,
            municipio.name,
            temperatura_actual,
            temperaturas.max,
            temperaturas.min,
            stateSky.description,
            proximos_dias.map { it.toDomain() }
        )
}