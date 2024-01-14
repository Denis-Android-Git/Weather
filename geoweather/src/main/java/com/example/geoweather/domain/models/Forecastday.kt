package com.example.geoweather.domain.models

import com.example.api.dto.DayDto

data class Forecastday(
    val date: String,
    val day: Day
)
