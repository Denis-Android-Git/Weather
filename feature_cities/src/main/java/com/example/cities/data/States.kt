package com.example.cities.data

import com.example.database.domain.models.WeatherFromDb

sealed class States {
    data object Loading : States()
    data class Success(val savedList: List<WeatherFromDb>) : States()
}