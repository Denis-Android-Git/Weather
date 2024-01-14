package com.example.api.dto

data class WeatherDto(
    val current: CurrentDto,
    val location: LocationDto,
    val forecast: ForecastDto?,
)
