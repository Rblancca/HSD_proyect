package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.ListProvinces

data class ListProvincesResponse(

    @SerializedName("title") val title : String,
    @SerializedName("provincias") val provinces : List<ProvinceResponse>,
    //@SerializedName("breadcrumb") val breadcrumb : List<com.hsd.contest.data.response.Breadcrumb>,
    @SerializedName("metadescripcion") val metadescripcion : String,
    @SerializedName("keywords") val keywordswords : String
): Mappable<ListProvinces>{
    override fun toDomain(): ListProvinces= ListProvinces(provinces.map { it.toDomain() })
}