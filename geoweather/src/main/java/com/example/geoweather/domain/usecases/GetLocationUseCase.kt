package com.example.geoweather.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.geoweather.domain.repo.ILocationService
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

class GetLocationUseCase(
    private val locationService: ILocationService
) {
    @RequiresApi(Build.VERSION_CODES.S)
    operator fun invoke(): Flow<LatLng?> = locationService.requestCurrentLocation()
}