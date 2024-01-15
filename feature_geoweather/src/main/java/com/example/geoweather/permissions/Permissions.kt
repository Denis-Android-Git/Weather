package com.example.geoweather.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.geoweather.data.PermissionEvent
import com.example.geoweather.viewmodel.GeoWeatherVM

class Permissions(
    private val context: Context,
    private val launcher: ActivityResultLauncher<Array<String>>,
    private val viewModel: GeoWeatherVM
) {
    companion object {
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        }.toTypedArray()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun checkPermissions() {
        val isAllGranted = REQUEST_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!isAllGranted) {
            launcher.launch(REQUEST_PERMISSIONS)
        } else {
            viewModel.handle(PermissionEvent.Granted)
        }
    }
}