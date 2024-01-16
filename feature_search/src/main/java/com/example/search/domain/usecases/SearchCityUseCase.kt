package com.example.search.domain.usecases

import com.example.search.domain.models.City
import com.example.search.domain.repo.SearchRepo

class SearchCityUseCase(
    private val repo: SearchRepo
) {
    suspend fun execute(city: String): List<City> {
        return repo.searchCity(city)
    }
}