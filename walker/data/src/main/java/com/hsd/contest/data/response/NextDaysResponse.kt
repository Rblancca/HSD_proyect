package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.WeatherInfo
import com.hsd.contest.domain.entities.WeatherNextDays

data class NextDaysResponse (
	@SerializedName("@attributes") val attributes : AttributesResponse,
	//@SerializedName("prob_precipitacion") val prob_precipitacion : List<Int>,
	//@SerializedName("cota_nieve_prov") val cota_nieve_prov : List<SnowResponse>,
	//@SerializedName("estado_cielo") val estado_cielo : List<Int>,
	//@SerializedName("viento") val viento : List<Viento>,
	//@SerializedName("racha_max") val racha_max : List<RachaMax>,
	@SerializedName("temperatura") val temperatura : Temperatura
	//@SerializedName("sens_termica") val sens_termica : Sens_termica,
	//@SerializedName("humedad_relativa") val humedad_relativa : HumidityResponse,
	//@SerializedName("uv_max") val uv_max : Int
): Mappable<WeatherNextDays>{
	override fun toDomain(): WeatherNextDays = WeatherNextDays(attributes.periodo,temperatura.maxima, temperatura.minima)
}