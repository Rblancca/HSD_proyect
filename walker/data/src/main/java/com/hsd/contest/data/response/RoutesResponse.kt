package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName

data class RoutesResponse(
    @SerializedName("summary")
    val summary: SummaryResponse,
    @SerializedName("resources")
    val resources: List<ResourcesResponse>
)