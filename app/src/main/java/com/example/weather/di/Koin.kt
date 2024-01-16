package com.example.weather.di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.api.retrofit.Retrofit
import com.example.cities.viewmodel.CitiesViewModel
import com.example.database.data.DbRepoImpl
import com.example.database.data.WeatherDataBase
import com.example.database.domain.dbrepo.DbRepo
import com.example.database.domain.usecase.DbUseCase
import com.example.geoweather.data.RepoGeoWeatherImpl
import com.example.geoweather.domain.repo.ILocationService
import com.example.geoweather.domain.repo.RepoGeoWeather
import com.example.geoweather.domain.usecases.GetForeCastUseCase
import com.example.geoweather.domain.usecases.GetGeoWeatherUseCase
import com.example.geoweather.domain.usecases.GetLocationUseCase
import com.example.geoweather.locationService.LocationService
import com.example.geoweather.viewmodel.GeoWeatherVM
import com.example.search.data.SearchRepoImpl
import com.example.search.domain.repo.SearchRepo
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
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDataBase::class.java,
            "WeatherDataBase"
        ).fallbackToDestructiveMigration().build()
    }
    single<DbRepo> { DbRepoImpl(get()) }
    single<SearchRepo> { SearchRepoImpl(get()) }
    single<RepoWeather> { RepoWeatherImpl(get()) }
    single<ILocationService> { LocationService(get(), get()) }
    single<RepoGeoWeather> { RepoGeoWeatherImpl(get()) }

    single<Retrofit> { Retrofit() }

    factory { SearchCityUseCase(get()) }
    factory { GetWeatherUseCase(get()) }
    factory { GetLocationUseCase(get()) }
    factory { GetGeoWeatherUseCase(get()) }
    factory { DbUseCase(get()) }
    factory { GetForeCastUseCase(get()) }


    viewModel { SearchViewModel(get(), get()) }
    viewModel { WeatherViewModel(get(), get()) }
    viewModel { GeoWeatherVM(get(), get(), get()) }
    viewModel { CitiesViewModel(get()) }
}