package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName


data class ThermalSensationResponse (
	@SerializedName("maxima") val maxima : Int,
	@SerializedName("minima") val minima : Int,
	@SerializedName("dato") val dato : List<Int>
)