package com.example.database.data

import com.example.database.domain.dbrepo.DbRepo
import com.example.database.domain.models.WeatherFromDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbRepoImpl(
    private val weatherDataBase: WeatherDataBase
) : DbRepo {
    override suspend fun upsertWeather(weatherFromDb: WeatherFromDb) {
        withContext(Dispatchers.IO) {
            weatherDataBase.weatherDao().upsertWeather(weatherFromDb)
        }
    }

    override suspend fun getWeatherList(): List<WeatherFromDb> {
        return withContext(Dispatchers.IO) {
            weatherDataBase.weatherDao().getList()
        }
    }

    override suspend fun searchWeatherInDb(id: Int): WeatherFromDb {
        return withContext(Dispatchers.IO) {
            weatherDataBase.weatherDao().getWeatherById(id)
        }
    }

    override suspend fun deleteItem(item: WeatherFromDb) {
        weatherDataBase.weatherDao().deleteItem(item)
    }
}