package com.example.search.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search.data.States
import com.example.search.domain.usecases.DbSearchUseCase
import com.example.search.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase,
    private val dbSearchUseCase: DbSearchUseCase
) : ViewModel() {

    private val _states = MutableLiveData<States>(
        States.Success(null, null)
    )
    val states = _states

    init {
        viewModelScope.launch {
            val result = dbSearchUseCase.executeList()
            _states.value = States.Success(null, result)
        }
    }

    fun searchCity(city: String) {
        viewModelScope.launch {
            try {
                if (city.length > 1) {
                    _states.value = States.Success(
                        searchCityUseCase.execute(city),
                        dbSearchUseCase.executeList()
                    )
                }
            } catch (e: Exception) {
                val result = dbSearchUseCase.executeList()
                if (result.isEmpty()) {
                    _states.value = States.Error
                } else {
                    _states.value = States.Success(
                        null,
                        result
                    )
                }
            }
        }
    }
}