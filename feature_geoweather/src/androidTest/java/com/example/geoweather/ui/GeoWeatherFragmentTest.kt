package com.example.geoweather.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.geoweather.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GeoWeatherFragmentTest {

    @Test
    fun checkWeatherInfoDisplayed() {
        val scenario = launchFragmentInContainer<GeoWeatherFragment>()

        onView(withId(R.id.feels_like))
            .check(matches(isDisplayed()))
    }
}
