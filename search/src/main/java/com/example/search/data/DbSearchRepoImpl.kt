package com.example.search.data

import com.example.database.data.WeatherDataBase
import com.example.database.domain.models.WeatherFromDb
import com.example.search.domain.repo.DbSearchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbSearchRepoImpl(
    private val weatherDataBase: WeatherDataBase
) : DbSearchRepo {
    override suspend fun getWeatherList(): List<WeatherFromDb> {
        return withContext(Dispatchers.IO) {
            weatherDataBase.weatherDao().getList()
        }
    }
}