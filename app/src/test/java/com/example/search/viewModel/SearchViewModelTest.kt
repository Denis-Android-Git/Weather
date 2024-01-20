package com.example.search.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.database.domain.models.WeatherFromDb
import com.example.database.domain.usecase.DbUseCase
import com.example.database.domain.usecase.FakeWeatherDataBase
import com.example.search.data.States
import com.example.search.domain.models.City
import com.example.search.domain.repo.SearchRepo
import com.example.search.domain.usecases.SearchCityUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private lateinit var repo: SearchRepo
    private lateinit var fakeWeatherDataBase: FakeWeatherDataBase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun stUp() = runTest {
        Dispatchers.setMain(Dispatchers.Unconfined)
        repo = FakeSearchRepoImpl()
        fakeWeatherDataBase = FakeWeatherDataBase()
        val weatherList = mutableListOf<WeatherFromDb>()
        ('a'..'d').forEachIndexed { index, c ->
            weatherList.add(
                WeatherFromDb(
                    id = index,
                    city = c.toString(),
                    country = c.toString(),
                    timeStamp = index.toLong(),
                    condition = c.toString(),
                    date = index.toString(),
                    feelsLike = c.toString(),
                    image = c.toString(),
                    temperature = c.toString(),
                    wind = c.toString()
                )
            )
        }
        runBlocking {
            weatherList.forEach {
                fakeWeatherDataBase.upsertWeather(it)
            }
        }
        viewModel = SearchViewModel(
            SearchCityUseCase(repo),
            DbUseCase(fakeWeatherDataBase),
        )
        viewModel.searchCity("dfdfdfdf")
    }

    @Test
    fun `Returns 2 lists`() {
        when (val state = viewModel.states.getOrAwaitValueTest()) {
            States.Error -> {}
            is States.Success -> {
                assertThat(state).isEqualTo(
                    States.Success(
                        state.cityList,
                        state.weatherList
                    )
                )
            }
        }
    }
}

class FakeSearchRepoImpl : SearchRepo {
    override suspend fun searchCity(city: String): List<City> {
        val list = listOf(
            City(
                country = "",
                id = 1,
                lat = 22.22,
                lon = 11.11,
                name = "",
                region = "",
                url = ""
            ),
            City(
                country = "",
                id = 2,
                lat = 22.22,
                lon = 11.11,
                name = "",
                region = "",
                url = ""
            )
        )
        return list
    }
}