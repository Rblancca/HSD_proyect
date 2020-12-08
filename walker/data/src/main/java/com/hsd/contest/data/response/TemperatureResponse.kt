package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName


data class TemperatureResponse (
	@SerializedName("maxima") val max : Int,
	@SerializedName("minima") val min : Int,
	@SerializedName("dato") val data : List<Int>
)