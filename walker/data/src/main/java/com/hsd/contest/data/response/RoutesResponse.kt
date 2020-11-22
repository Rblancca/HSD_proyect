package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.Routes

data class RoutesResponse(
    @SerializedName("summary")
    val summary: SummaryResponse,
    @SerializedName("resources")
    val resources: List<RouteInfoResponse>
) : Mappable<Routes> {
    override fun toDomain(): Routes = Routes(resources.map { it.toDomain() })
}