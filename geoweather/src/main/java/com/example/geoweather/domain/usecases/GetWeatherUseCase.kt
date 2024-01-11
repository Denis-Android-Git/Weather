package com.example.geoweather.domain.usecases

import com.example.geoweather.domain.models.Weather
import com.example.geoweather.domain.repo.RepoGeoWeather

class GetGeoWeatherUseCase(
    private val repoWeather: RepoGeoWeather
) {
    suspend fun execute(id: String): Weather {
        return repoWeather.getWeather(id)
    }
}