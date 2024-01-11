package com.example.weather.data

sealed class States {
    data object Success : States()
    data object Loading : States()
}