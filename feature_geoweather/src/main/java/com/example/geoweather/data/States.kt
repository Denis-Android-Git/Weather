package com.example.geoweather.data

import com.example.geoweather.domain.models.Weather
import com.google.android.gms.maps.model.LatLng

sealed interface ViewState {
    data class Success(val location: LatLng?) : ViewState
    data object RevokedPermissions : ViewState
}

sealed interface PermissionEvent {
    data object Granted : PermissionEvent
    data object Revoked : PermissionEvent
}

sealed class States {
    data class Success(
        val weather: Weather?,
        val foreCast: Weather?
    ) : States()

    data object Loading : States()
    data class Error(
        val error: String?
    ) : States()
}