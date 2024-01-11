package com.example.geoweather.domain.repo

import com.example.geoweather.domain.models.Weather

interface RepoGeoWeather {
    suspend fun getWeather(id: String): Weather
}