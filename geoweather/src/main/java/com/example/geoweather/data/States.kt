package com.example.geoweather.data

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
    data object Success : States()
    data object Loading : States()
}