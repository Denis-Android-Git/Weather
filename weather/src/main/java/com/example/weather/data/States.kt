package com.example.weather.data

import com.example.database.domain.models.WeatherFromDb
import com.example.weather.domain.models.Weather

sealed class States {
    data class Success(val weather: Weather) : States()
    data class SuccessDb(val weather: WeatherFromDb) : States()
    data object Loading : States()
}