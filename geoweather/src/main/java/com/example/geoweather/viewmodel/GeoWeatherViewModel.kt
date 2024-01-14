package com.example.geoweather.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoweather.data.PermissionEvent
import com.example.geoweather.data.States
import com.example.geoweather.data.ViewState
import com.example.geoweather.domain.models.Weather
import com.example.geoweather.domain.usecases.GetForeCastUseCase
import com.example.geoweather.domain.usecases.GetGeoWeatherUseCase
import com.example.geoweather.domain.usecases.GetLocationUseCase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
class GeoWeatherVM(
    private val getLocationUseCase: GetLocationUseCase,
    private val getGeoWeatherUseCase: GetGeoWeatherUseCase,
    private val getForeCastUseCase: GetForeCastUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>(ViewState.RevokedPermissions)
    val viewState: LiveData<ViewState> = _viewState

    private val _weather = MutableLiveData<Weather?>(null)
    val weather: LiveData<Weather?> = _weather

    private val _state = MutableLiveData<States>(States.Loading)
    val state: LiveData<States> = _state

    private val _foreCast = MutableLiveData<Weather?>(null)
    val foreCast: LiveData<Weather?> = _foreCast

    fun getWeather(lat: String, long: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            try {
                val location = "$lat,$long"
                _weather.value = getGeoWeatherUseCase.execute(location)
                _state.value = States.Success
            } catch (e: Exception) {
                _state.value = States.Error
            }
        }
    }

    fun getForeCast(lat: String, long: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            //try {
            val location = "$lat,$long"
            _foreCast.value = getForeCastUseCase.execute(location)
            _state.value = States.Success
            //} catch (e: Exception) {
            //_state.value = States.Error
            //}
        }
    }

    fun handle(event: PermissionEvent) {
        _viewState.value = ViewState.Success(null)
        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUseCase.invoke().collect {
                        _viewState.value = ViewState.Success(it)
                    }
                }
            }

            PermissionEvent.Revoked -> {
                _viewState.value = ViewState.RevokedPermissions
            }
        }
    }
}