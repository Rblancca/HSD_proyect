package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName


data class ProbStrong (
	@SerializedName("@attributes") val attributes : AttributesResponse
)