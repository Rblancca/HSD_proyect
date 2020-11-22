package com.hsd.contest.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RouteInfo(
    val id: String,
    val municipality: String,
    val name: String,
    val info: String,
    val province: String,
    val heritage: String,
    val signal: String,
    val longitude: Double,
    val modified: String,
    val travel: String,
    val distance: Int,
    val difficulty: String,
    val cotMax: Int,
    val protection: String,
    val duration: String,
    val type: String,
    val cotMin: Int,
    val latitude: Double,
    val permission: String,
    val uri: String
): Parcelable