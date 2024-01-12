package com.example.search.data

import com.example.database.domain.models.WeatherFromDb
import com.example.search.domain.models.City

sealed class States {
    data class Success(
        val cityList: List<City>?,
        val weatherList: List<WeatherFromDb>?
    ) : States()

    data object Error : States()
}