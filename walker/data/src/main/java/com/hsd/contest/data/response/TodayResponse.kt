package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class TodayResponse (
	@SerializedName("@attributes") val attributes : AttributesResponse,
	@SerializedName("estado_cielo") val sky_state : List<String>,
	@SerializedName("precipitacion") val rain : List<Int>,
	@SerializedName("prob_precipitacion") val probRain : List<ProbRainResponse>,
	@SerializedName("prob_tormenta") val probStrong : List<ProbStrongResponse>,
	@SerializedName("nieve") val snow : List<Int>,
	@SerializedName("prob_nieve") val probSnow : List<ProbSnowResponse>,
	@SerializedName("temperatura") val temperature : List<Int>,
	@SerializedName("sens_termica") val sensThermal : List<Int>,
	@SerializedName("humedad_relativa") val rh : List<Int>,
	@SerializedName("viento") val wind : List<WindResponse>,
	@SerializedName("racha_max") val maximumStreak: List<Int>
)