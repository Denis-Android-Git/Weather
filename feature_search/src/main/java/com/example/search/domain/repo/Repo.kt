package com.example.search.domain.repo

import com.example.search.domain.models.City

interface Repo {
    suspend fun searchCity(city: String): List<City>
}