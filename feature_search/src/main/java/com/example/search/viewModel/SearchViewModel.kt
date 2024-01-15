package com.example.search.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.domain.usecase.DbUseCase
import com.example.search.data.States
import com.example.search.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCityUseCase: SearchCityUseCase,
    private val dbUseCase: DbUseCase
) : ViewModel() {

    private val _states = MutableLiveData<States>(
        States.Success(null, null)
    )
    val states = _states

    init {
        viewModelScope.launch {
            val result = dbUseCase.executeList()
            _states.value = States.Success(null, result)
        }
    }

    fun searchCity(city: String) {
        viewModelScope.launch {
            try {
                if (city.length > 1) {
                    _states.value = States.Success(
                        searchCityUseCase.execute(city),
                        dbUseCase.executeList()
                    )
                }
            } catch (e: Exception) {
                val result = dbUseCase.executeList()
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