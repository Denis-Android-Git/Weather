package com.example.search.domain.repo

import com.example.database.domain.models.WeatherFromDb

interface DbSearchRepo {
    suspend fun getWeatherList(): List<WeatherFromDb>
}