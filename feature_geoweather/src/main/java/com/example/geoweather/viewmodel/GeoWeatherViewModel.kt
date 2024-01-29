package com.example.geoweather.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.domain.usecase.DbUseCase
import com.example.geoweather.data.PermissionEvent
import com.example.geoweather.data.States
import com.example.geoweather.data.ViewState
import com.example.geoweather.domain.usecases.GetForeCastUseCase
import com.example.geoweather.domain.usecases.GetGeoWeatherUseCase
import com.example.geoweather.domain.usecases.GetLocationUseCase
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
class GeoWeatherVM(
    private val getLocationUseCase: GetLocationUseCase,
    private val getGeoWeatherUseCase: GetGeoWeatherUseCase,
    private val getForeCastUseCase: GetForeCastUseCase,
    private val dbUseCase: DbUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>(ViewState.RevokedPermissions)
    val viewState: LiveData<ViewState> = _viewState

    private val _state = MutableLiveData<States>(States.Loading)
    val state: LiveData<States> = _state

    init {
        viewModelScope.launch {
            val oldList = dbUseCase.executeList().filter {
                it.timeStamp <= System.currentTimeMillis() - 30L * 24L * 60L * 60L * 1000L
            }
            oldList.forEach {
                dbUseCase.executeDelete(it)
            }
        }
    }

    fun getWeather(lat: String, long: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            try {
                val location = "$lat,$long"
                _state.value = States.Success(
                    getGeoWeatherUseCase.execute(location),
                    getForeCastUseCase.execute(location)
                )
            } catch (e: Exception) {
                _state.value = States.Error(e.message)
            }
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