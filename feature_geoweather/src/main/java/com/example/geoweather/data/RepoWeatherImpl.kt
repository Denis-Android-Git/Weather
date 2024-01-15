package com.example.geoweather.data

import com.example.api.retrofit.Retrofit
import com.example.geoweather.domain.models.Weather
import com.example.geoweather.domain.models.toWeather
import com.example.geoweather.domain.repo.RepoGeoWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoGeoWeatherImpl(
    private val api: Retrofit
) : RepoGeoWeather {
    override suspend fun getWeather(id: String): Weather {
        return withContext(Dispatchers.IO) {
            api.api.getWeather(q = id).toWeather()
        }
    }

    override suspend fun getForecast(id: String): Weather {
        return withContext(Dispatchers.IO) {
            api.api.getForecast(q = id).toWeather()
        }
    }
}