package com.example.search.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search.domain.models.City
import com.example.search.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    private val _cityList = MutableLiveData<List<City>>(emptyList())
    val cityList: LiveData<List<City>> = _cityList

    fun searchCity(city: String) {
        viewModelScope.launch {
            if (city.length > 1) {
                _cityList.value = searchCityUseCase.execute(city)
            }
        }
    }
}