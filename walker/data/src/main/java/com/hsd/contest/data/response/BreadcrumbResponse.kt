package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class BreadcrumbResponse (
	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String,
	@SerializedName("title") val title : String
)