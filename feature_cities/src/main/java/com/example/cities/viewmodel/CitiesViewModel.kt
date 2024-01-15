package com.example.cities.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cities.data.States
import com.example.database.domain.usecase.DbUseCase
import kotlinx.coroutines.launch

class CitiesViewModel(
    private val dbUseCase: DbUseCase
) : ViewModel() {

    private val _states = MutableLiveData<States>(States.Loading)
    val states = _states

    fun getList() {
        _states.value = States.Loading
        viewModelScope.launch {
            _states.value = States.Success(dbUseCase.executeList())
        }
    }
}