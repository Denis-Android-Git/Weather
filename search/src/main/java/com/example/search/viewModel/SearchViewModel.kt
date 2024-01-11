package com.example.search.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search.data.States
import com.example.search.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    private val _states = MutableLiveData<States>(States.Success(null))
    val states = _states

    fun searchCity(city: String) {
        viewModelScope.launch {
            try {
                if (city.length > 1) {
                    _states.value = States.Success(searchCityUseCase.execute(city))
                }
            } catch (e: Exception) {
                _states.value = States.Error
            }
        }
    }
}