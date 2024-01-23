package com.example.geoweather

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GeoWeatherFragmentTest {
    @Test
    fun checkTextIsDisplayed() {
        val expectedText = "Hello, first UI test"
        Espresso.onView(withId(R.id.city))
    }
}