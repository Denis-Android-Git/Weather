package com.example.weather.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.api.retrofit.Retrofit
import com.example.geoweather.data.RepoGeoWeatherImpl
import com.example.geoweather.domain.repo.ILocationService
import com.example.geoweather.domain.repo.RepoGeoWeather
import com.example.geoweather.domain.usecases.GetGeoWeatherUseCase
import com.example.geoweather.domain.usecases.GetLocationUseCase
import com.example.geoweather.locationService.LocationService
import com.example.geoweather.viewmodel.GeoWeatherVM
import com.example.search.data.RepoImpl
import com.example.search.domain.repo.Repo
import com.example.search.domain.usecases.SearchCityUseCase
import com.example.search.viewModel.SearchViewModel
import com.example.weather.data.RepoWeatherImpl
import com.example.weather.domain.repoweather.RepoWeather
import com.example.weather.domain.useCase.GetWeatherUseCase
import com.example.weather.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.S)
val module = module {
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(
            androidContext()
        )
    }

    single<Repo> { RepoImpl(get()) }
    single<RepoWeather> { RepoWeatherImpl(get()) }
    single<ILocationService> { LocationService(get(), get()) }
    single<RepoGeoWeather> { RepoGeoWeatherImpl(get()) }

    single<Retrofit> { Retrofit() }

    factory { SearchCityUseCase(get()) }
    factory { GetWeatherUseCase(get()) }
    factory { GetLocationUseCase(get()) }
    factory { GetGeoWeatherUseCase(get()) }


    viewModel { SearchViewModel(get()) }
    viewModel { WeatherViewModel(get()) }
    viewModel { GeoWeatherVM(get(), get()) }

}