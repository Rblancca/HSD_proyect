package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class TomorrowResponse (
	@SerializedName("@attributes") val attributes : AttributesResponse,
	@SerializedName("estado_cielo") val skyState : List<String>,
	@SerializedName("precipitacion") val rain : List<Int>,
	@SerializedName("prob_precipitacion") val probRain : List<Int>,
	@SerializedName("prob_tormenta") val prodStrong : List<Int>,
	@SerializedName("nieve") val snow : List<Int>,
	@SerializedName("prob_nieve") val prodSnow : List<Int>,
	@SerializedName("temperatura") val temperature : List<Int>,
	@SerializedName("sens_termica") val thermalSensation: List<Int>,
	@SerializedName("humedad_relativa") val rh : List<Int>,
	@SerializedName("viento") val wind : List<WindResponse>,
	@SerializedName("racha_max") val racha_max : List<Int>
)