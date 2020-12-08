package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class StateSkyResponse (
	@SerializedName("description") val description : String,
	@SerializedName("id") val id : String
)