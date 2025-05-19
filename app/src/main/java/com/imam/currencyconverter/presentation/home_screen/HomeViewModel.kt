package com.imam.currencyconverter.presentation.home_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.FromCountrySelected -> {
                _uiState.update {
                    it.copy(
                        fromCountryCode = event.countryCode,
                        fromCurrencyCode = event.currencyCode
                    )
                }
            }

            is HomeEvents.ToCountrySelected -> {
                _uiState.update {
                    it.copy(
                        toCountryCode = event.countryCode,
                        toCurrencyCode = event.currencyCode
                    )
                }
            }

            is HomeEvents.CurrencyAmount -> {
                _uiState.update {
                    it.copy(currentAmount = event.amount)
                }
            }
            is HomeEvents.SearchQueryChanged -> {  // Handle search query
                _uiState.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is HomeEvents.SwapCurrencies -> {
                _uiState.update {
                    it.copy(
                        fromCountryCode = it.toCountryCode,
                        toCountryCode = it.fromCountryCode,
                        fromCurrencyCode = it.toCurrencyCode,
                        toCurrencyCode = it.fromCurrencyCode
                    )
                }


            }
        }
    }

    fun swapCurrencies() {
        _uiState.update {
            it.copy(
                fromCountryCode = it.toCountryCode,
                toCountryCode = it.fromCountryCode,
                fromCurrencyCode = it.toCurrencyCode,
                toCurrencyCode = it.fromCurrencyCode
            )
        }
    }



}