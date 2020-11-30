package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.Municipality

data class MunicipalityResponse(
    @SerializedName("NOMBRE") val name: String,
    @SerializedName("CODIGOINE") val code: String,
): Mappable<Municipality>{
    override fun toDomain(): Municipality = Municipality(name, code)
}