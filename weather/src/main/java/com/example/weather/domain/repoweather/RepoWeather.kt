package com.example.weather.domain.repoweather

import com.example.weather.domain.models.Weather

interface RepoWeather {
    suspend fun getWeather(id: String): Weather
}