package com.example.weather.domain.useCase

import com.example.database.domain.models.WeatherFromDb
import com.example.weather.domain.repoweather.DbRepo

class DbUseCase(
    private val dbRepo: DbRepo
) {
    suspend fun upsert(weatherFromDb: WeatherFromDb) {
        dbRepo.upsertWeather(weatherFromDb)
    }

    suspend fun executeList(): List<WeatherFromDb> {
        return dbRepo.getWeatherList()
    }

    suspend fun executeSearch(id: Int): WeatherFromDb? {
        return dbRepo.searchWeatherInDb(id)
    }
}