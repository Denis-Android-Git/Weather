package com.example.database.domain.dbrepo

import com.example.database.domain.models.WeatherFromDb

interface DbRepo {
    suspend fun upsertWeather(weatherFromDb: WeatherFromDb)
    suspend fun getWeatherList(): List<WeatherFromDb>
    suspend fun searchWeatherInDb(id: Int): WeatherFromDb?
    suspend fun deleteItem(item: WeatherFromDb)
}