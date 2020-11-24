package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class Viento (
	@SerializedName("@attributes") val attributes : AttributesResponse,
	@SerializedName("direccion") val direccion : String,
	@SerializedName("velocidad") val velocidad : Int
)