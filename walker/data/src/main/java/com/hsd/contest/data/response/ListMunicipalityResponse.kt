package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.ListMunicipality

data class ListMunicipalityResponse(
    @SerializedName("municipios") val municipalities : List<MunicipalityResponse>,
) : Mappable<ListMunicipality> {
    override fun toDomain(): ListMunicipality =
        ListMunicipality(municipalities.map { it.toDomain() })
}