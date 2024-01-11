package com.example.api.retrofit

import com.example.api.dto.CityDto
import com.example.api.dto.WeatherDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "ed9af5a33a5e4abb826122617240701"

class Retrofit {
    val api: SearchApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .build()
            .create(SearchApi::class.java)
    }

    interface SearchApi {

        @GET("search.json")
        suspend fun searchCity(
            @Query("key") key: String = API_KEY,
            @Query("q") q: String,
        ): List<CityDto>

        @GET("current.json")
        suspend fun getWeather(
            @Query("key") key: String = API_KEY,
            @Query("q") q: String
        ): WeatherDto

        @GET("current.json")
        suspend fun getWeatherByGPS(
            @Query("key") key: String = API_KEY,
            @Query("q") q: String
        ): WeatherDto

    }
}