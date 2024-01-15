package com.example.weather.domain.useCase

import com.example.weather.domain.models.Weather
import com.example.weather.domain.repoweather.RepoWeather

class GetWeatherUseCase(
    private val repoWeather: RepoWeather
) {
    suspend fun execute(id: String): Weather {
        return repoWeather.getWeather(id)
    }
}