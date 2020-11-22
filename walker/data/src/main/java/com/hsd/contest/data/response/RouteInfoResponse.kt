package com.hsd.contest.data.response

import com.google.gson.annotations.SerializedName
import com.hsd.contest.domain.entities.RouteInfo

data class RouteInfoResponse(
    @SerializedName("ca:municipio") val municipality: String,
    @SerializedName("ca:nombre") val name: String,
    @SerializedName("ca:info") val info: String,
    @SerializedName("ca:provincia") val province: String,
    @SerializedName("ca:patrimonio") val heritage: String,
    @SerializedName("ca:senal") val signal: String,
    @SerializedName("ca:coord_longitud") val longitude: Double,
    @SerializedName("dc:modified") val modified: String,
    @SerializedName("ca:recorrido") val travel: String,
    @SerializedName("ca:distancia") val distance: Int,
    @SerializedName("ca:dificultad") val difficulty: String,
    @SerializedName("ca:cotamax") val cotMax: Int,
    @SerializedName("ca:proteccion") val protection: String,
    @SerializedName("ca:duracion") val duration: String,
    @SerializedName("ca:tipo") val type: String,
    @SerializedName("ca:cotamin") val cotMin: Int,
    @SerializedName("ca:coord_latitud") val latitude: Double,
    @SerializedName("ca:permiso") val permission: String,
    @SerializedName("uri") val uri: String
) : Mappable<RouteInfo> {
    override fun toDomain(): RouteInfo =
        RouteInfo(
            imageId(uri),
            municipality,
            name,
            info,
            province,
            heritage,
            signal,
            longitude,
            modified,
            travel,
            distance,
            difficulty,
            cotMax,
            protection,
            duration,
            type,
            cotMin,
            latitude,
            permission,
            uri
        )

    fun imageId(uri: String): String {
        val id = uri.substringAfter("senderos/").substringBefore(".json")
        return when {
            id.length == 2 -> "2$id"
            id.length > 2 -> "2${id.substring(0, 2)}"
            else -> "20$id"
        }
    }
}