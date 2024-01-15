package com.example.geoweather.domain.usecases

import com.example.geoweather.domain.models.Weather
import com.example.geoweather.domain.repo.RepoGeoWeather

class GetForeCastUseCase(
    private val repoGeoWeather: RepoGeoWeather
) {
    suspend fun execute(id: String): Weather {
        return repoGeoWeather.getForecast(id)
    }
}