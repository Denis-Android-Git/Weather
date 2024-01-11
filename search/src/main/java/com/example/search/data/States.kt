package com.example.search.data

import com.example.search.domain.models.City

sealed class States {
    data class Success(
        val cityList: List<City>?
    ) : States()

    data object Error : States()
}