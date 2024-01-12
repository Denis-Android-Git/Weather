package com.example.search.domain.usecases

import com.example.database.domain.models.WeatherFromDb
import com.example.search.domain.repo.DbSearchRepo

class DbSearchUseCase(
    private val dbSearchRepo: DbSearchRepo
) {
    suspend fun executeList(): List<WeatherFromDb> {
        return dbSearchRepo.getWeatherList()
    }
}