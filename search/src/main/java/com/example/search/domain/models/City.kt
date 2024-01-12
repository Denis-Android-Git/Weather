package com.example.search.domain.models

import com.example.api.dto.CityDto
import com.example.database.entity.WeatherEntity

data class City(
    val country: String,
    override val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val region: String,
    val url: String
) : WeatherEntity

fun CityDto.toCity() = City(
    country = country,
    id = id,
    lat = lat,
    lon = lon,
    name = name,
    region = region,
    url = url
)