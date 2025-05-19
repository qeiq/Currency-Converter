package com.imam.currencyconverter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imam.currencyconverter.domain.model.CurrencyRate
import com.imam.currencyconverter.domain.usecase.GetRatesUseCase
import com.imam.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ApiState())
    val state = _state.asStateFlow()

    fun getRates(base: String) = viewModelScope.launch {
        getRatesUseCase(base).collect { data ->
            when (data) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isError = data.errorMessage ?: "Unknown error",
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(isLoading = false, data = data.data)
                    }
                }
            }
        }
    }
}


