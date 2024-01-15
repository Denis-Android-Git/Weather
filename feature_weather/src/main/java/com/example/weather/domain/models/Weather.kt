package com.example.weather.domain.models

import com.example.api.dto.ConditionDto
import com.example.api.dto.CurrentDto
import com.example.api.dto.LocationDto
import com.example.api.dto.WeatherDto

data class Weather(
    val current: Current,
    val location: Location
)

fun WeatherDto.toWeather() = Weather(
    current = current.toCurrent(),
    location = location.toLocation()
)

fun LocationDto.toLocation() = Location(
    country = country,
    lat = lat,
    localtime = localtime,
    localtime_epoch = localtime_epoch,
    lon = lon,
    name = name,
    region = region,
    tz_id = tz_id
)

fun CurrentDto.toCurrent() = Current(
    cloud = cloud,
    condition = condition.toCondition(),
    feelslike_c = feelslike_c,
    feelslike_f = feelslike_f,
    gust_kph = gust_kph,
    gust_mph = gust_mph,
    humidity = humidity,
    is_day = is_day,
    last_updated = last_updated,
    last_updated_epoch = last_updated_epoch,
    precip_in = precip_in,
    precip_mm = precip_mm,
    pressure_in = pressure_in,
    pressure_mb = pressure_mb,
    temp_c = temp_c,
    temp_f = temp_f,
    uv = uv,
    vis_km = vis_km,
    vis_miles = vis_miles,
    wind_degree = wind_degree,
    wind_dir = wind_dir,
    wind_kph = wind_kph,
    wind_mph = wind_mph
)

fun ConditionDto.toCondition() = Condition(
    code = code,
    icon = icon,
    text = text
)
