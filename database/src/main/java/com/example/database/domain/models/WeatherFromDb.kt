package com.example.database.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.entity.WeatherEntity

@Entity
data class WeatherFromDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    override val id: Int,
    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "temperature")
    val temperature: String,
    @ColumnInfo(name = "feelsLike")
    val feelsLike: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "wind")
    val wind: String,
    @ColumnInfo(name = "condition")
    val condition: String
) : WeatherEntity
