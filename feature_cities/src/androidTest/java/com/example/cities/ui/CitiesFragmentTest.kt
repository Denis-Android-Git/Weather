package com.example.cities.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cities.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CitiesFragmentTest {

//    @Before
//    fun setUp() {
//        val testModule = module {
//
//            viewModel { CitiesViewModel(get()) }
//
//        }
//
//        startKoin {
//            androidContext(mockk())
//            modules(testModule)
//        }
//    }

    @Test
    fun checkWeatherInfoDisplayed() {
        val scenario = launchFragmentInContainer<CitiesFragment>(
        )

        onView(withId(R.id.text_dashboard))
            .check(matches(ViewMatchers.withText("")))
    }
}