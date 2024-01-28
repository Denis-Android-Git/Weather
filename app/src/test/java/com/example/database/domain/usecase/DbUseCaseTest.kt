package com.example.database.domain.usecase

import com.example.database.domain.dbrepo.DbRepo
import com.example.database.domain.models.WeatherFromDb
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DbUseCaseTest {
    private lateinit var dbUseCase: DbUseCase
    private lateinit var fakeWeatherDataBase: FakeWeatherDataBase

    @Before
    fun setUp() {
        fakeWeatherDataBase = FakeWeatherDataBase()
        dbUseCase = DbUseCase(fakeWeatherDataBase)

        val fakeData = mutableListOf<WeatherFromDb>()
        ('a'..'z').forEachIndexed { index, c ->
            fakeData.add(
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

        fakeData.sortByDescending {
            it.timeStamp
        }
        runBlocking {
            fakeData.forEach {
                fakeWeatherDataBase.upsertWeather(it)
            }
        }
    }

    @Test
    fun `Returns correct list`() = runBlocking {
        val weatherList = dbUseCase.executeList()
        for (i in 0..weatherList.size - 2) {
            assertThat(weatherList[i].timeStamp).isGreaterThan(weatherList[i + 1].timeStamp)
        }
    }
}

class DbUseCaseTest2 {
    private lateinit var dbUseCase: DbUseCase
    private lateinit var fakeWeatherDataBase: FakeWeatherDataBase

    @Before
    fun upsertItem() {
        fakeWeatherDataBase = FakeWeatherDataBase()
        dbUseCase = DbUseCase(fakeWeatherDataBase)

        val weatherList = mutableListOf<WeatherFromDb>()

        runBlocking {
            weatherList.forEach {
                fakeWeatherDataBase.upsertWeather(it)
            }
        }
    }

    @Test
    fun `Upserts item in a list`() = runBlocking {
        val item = WeatherFromDb(
            city = "n",
            country = "",
            timeStamp = 10000,
            condition = "",
            date = "",
            feelsLike = "",
            image = "",
            temperature = "c.toString()",
            wind = "c.toString()",
            id = 1
        )
        dbUseCase.upsert(item)
        val list = dbUseCase.executeList()
        assertThat(list).isNotEmpty()
    }
}

class DbUseCaseTest3 {
    private lateinit var dbUseCase: DbUseCase
    private lateinit var fakeWeatherDataBase: FakeWeatherDataBase

    @Before
    fun findItemById() {
        fakeWeatherDataBase = FakeWeatherDataBase()
        dbUseCase = DbUseCase(fakeWeatherDataBase)
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
    }

    @Test
    fun `Is ID found`() = runBlocking {
        val list = dbUseCase.executeList()
        val item = dbUseCase.executeSearch(3)
        assertThat(list).contains(item)
    }
}

class FakeWeatherDataBase : DbRepo {

    private val weatherList = mutableListOf<WeatherFromDb>()
    override suspend fun upsertWeather(weatherFromDb: WeatherFromDb) {
        weatherList.add(weatherFromDb)
    }

    override suspend fun getWeatherList(): List<WeatherFromDb> {
        return weatherList
    }

    override suspend fun searchWeatherInDb(id: Int): WeatherFromDb? {
        return weatherList.find {
            it.id == id
        }
    }

    override suspend fun deleteItem(item: WeatherFromDb) {
        TODO("Not yet implemented")
    }
}