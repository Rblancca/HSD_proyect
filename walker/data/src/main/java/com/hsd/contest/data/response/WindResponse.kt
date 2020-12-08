package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class WindResponse (
	@SerializedName("@attributes") val attributes : AttributesResponse,
	@SerializedName("direccion") val direction : String,
	@SerializedName("velocidad") val velocity : Int
)