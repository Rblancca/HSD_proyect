package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.Province

data class ProvinceResponse(
    @SerializedName("CODPROV") val codeProv: Int,
    @SerializedName("NOMBRE_PROVINCIA") val provinceName: String,
    @SerializedName("CODAUTON") val dauton: Int,
    @SerializedName("COMUNIDAD_CIUDAD_AUTONOMA") val city: String,
    @SerializedName("CAPITAL_PROVINCIA") val capital: String
) : Mappable<Province> {
    override fun toDomain(): Province = Province(codeProv, provinceName)
}