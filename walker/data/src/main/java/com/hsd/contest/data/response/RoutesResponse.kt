package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.Routes

data class RoutesResponse(
    @SerializedName("summary")
    val summary: SummaryResponse,
    @SerializedName("resources")
    val resources: List<RouteInfoResponse>
): Mappable<com.hsd.contest.domain.entities.Routes>{
    override fun toDomain(): com.hsd.contest.domain.entities.Routes =
        com.hsd.contest.domain.entities.Routes(resources.map { it.toDomain() })
}