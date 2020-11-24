package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse (
	@SerializedName("hoy") val hoy : TodayResponse,
	@SerializedName("manana") val manana : TomorrowResponse
)