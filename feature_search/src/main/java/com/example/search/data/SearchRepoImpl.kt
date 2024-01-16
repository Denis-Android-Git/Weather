package com.example.search.data

import com.example.api.retrofit.Retrofit
import com.example.search.domain.models.City
import com.example.search.domain.models.toCity
import com.example.search.domain.repo.SearchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepoImpl(
    private val api: Retrofit
) : SearchRepo {

    override suspend fun searchCity(city: String): List<City> {
        return withContext(Dispatchers.IO) {
            api.api.searchCity(q = city).map {
                it.toCity()
            }
        }
    }
}