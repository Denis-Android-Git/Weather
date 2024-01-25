package com.example.weather

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.database.data.WeatherDataBase
import com.example.weather.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(module)
        }
    }
}