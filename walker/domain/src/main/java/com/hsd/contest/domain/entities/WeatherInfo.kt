package com.hsd.contest.domain.entities

data class WeatherInfo(
    val date: String,
    val nameMunicipality: String,
    val actualTemperature: Int,
    val temperatureMax: Int,
    val temperatureMin: Int,
    val skyState: String,
    val weatherNextDays: List<WeatherNextDays>
)