package com.example.search.domain.usecases

import com.example.search.domain.models.City
import com.example.search.domain.repo.Repo

class SearchCityUseCase(
    private val repo: Repo
) {
    suspend fun execute(city: String): List<City> {
        return repo.searchCity(city)
    }
}