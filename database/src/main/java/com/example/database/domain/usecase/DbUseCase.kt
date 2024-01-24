package com.example.database.domain.usecase

import com.example.database.domain.dbrepo.DbRepo
import com.example.database.domain.models.WeatherFromDb

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
    suspend fun executeDelete(timeStampOfThirtyDays: Long) {
        dbRepo.deleteOldData(timeStampOfThirtyDays)
    }
}