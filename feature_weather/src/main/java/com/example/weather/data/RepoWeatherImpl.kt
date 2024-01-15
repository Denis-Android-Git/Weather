package com.example.weather.data

import com.example.api.retrofit.Retrofit
import com.example.weather.domain.models.Weather
import com.example.weather.domain.models.toWeather
import com.example.weather.domain.repoweather.RepoWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoWeatherImpl(
    private val api: Retrofit
) : RepoWeather {
    override suspend fun getWeather(id: String): Weather {
        return withContext(Dispatchers.IO) {
            api.api.getWeather(q = id).toWeather()
        }
    }
}