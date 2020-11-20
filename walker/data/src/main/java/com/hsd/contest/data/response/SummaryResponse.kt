package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class SummaryResponse (
	@SerializedName("items") val items : Int,
	@SerializedName("items_per_page") val itemsPerPage : Int,
	@SerializedName("pages") val pages : Int,
	@SerializedName("current_page") val currentPage : Int
)