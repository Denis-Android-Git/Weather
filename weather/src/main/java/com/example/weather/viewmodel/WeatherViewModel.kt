package com.example.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.domain.models.WeatherFromDb
import com.example.weather.data.States
import com.example.weather.domain.useCase.DbUseCase
import com.example.weather.domain.useCase.GetWeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val dbUseCase: DbUseCase
) : ViewModel() {

    private val _state = MutableLiveData<States>(States.Loading)
    val state: LiveData<States> = _state

    fun upsertWeather(
        id: Int,
        city: String,
        country: String,
        date: String,
        temperature: String,
        feelsLike: String,
        image: String,
        wind: String,
        condition: String
    ) {
        viewModelScope.launch {
            val weatherFromDb = WeatherFromDb(
                id = id,
                city = city,
                country = country,
                date = date,
                temperature = temperature,
                feelsLike = feelsLike,
                image = image,
                wind = wind,
                condition = condition
            )
            dbUseCase.upsert(weatherFromDb)
        }
    }

    fun getWeather(id: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            try {
                val correctId = "id:$id"
                _state.value = States.Success(getWeatherUseCase.execute(correctId))
            } catch (e: Exception) {
                val correctId = id.toInt()
                _state.value = States.SuccessDb(dbUseCase.executeSearch(correctId))
            }
        }
    }
}