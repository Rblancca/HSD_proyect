package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class TodayResponse (
	@SerializedName("@attributes") val attributes : AttributesResponse,
	@SerializedName("estado_cielo") val estado_cielo : List<String>,
	@SerializedName("precipitacion") val precipitacion : List<Int>,
	@SerializedName("prob_precipitacion") val prob_precipitacion : List<ProbRainResponse>,
	@SerializedName("prob_tormenta") val prob_tormenta : List<ProbStrong>,
	@SerializedName("nieve") val nieve : List<Int>,
	@SerializedName("prob_nieve") val prob_nieve : List<ProbSnowResponse>,
	@SerializedName("temperatura") val temperatura : List<Int>,
	@SerializedName("sens_termica") val sens_termica : List<Int>,
	@SerializedName("humedad_relativa") val humedad_relativa : List<Int>,
	@SerializedName("viento") val viento : List<Viento>,
	@SerializedName("racha_max") val racha_max : List<Int>
)