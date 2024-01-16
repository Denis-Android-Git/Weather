package com.example.search.domain.repo

import com.example.search.domain.models.City

interface SearchRepo {
    suspend fun searchCity(city: String): List<City>
}