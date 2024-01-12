package com.example.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.domain.models.WeatherFromDb

@Database(
    entities = [
        WeatherFromDb::class
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}