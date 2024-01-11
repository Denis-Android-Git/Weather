package com.example.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.States
import com.example.weather.domain.models.Weather
import com.example.weather.domain.useCase.GetWeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weather = MutableLiveData<Weather?>(null)
    val weather: LiveData<Weather?> = _weather

    private val _state = MutableLiveData<States>(States.Success)
    val state: LiveData<States> = _state

    fun getWeather(id: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            val correctId = "id:$id"
            _weather.value = getWeatherUseCase.execute(correctId)
            _state.value = States.Success
        }
    }
}