package com.imam.currencyconverter.presentation

import com.imam.currencyconverter.domain.model.CurrencyRate

data class ApiState(
    val isLoading: Boolean = false,
    val data: CurrencyRate? = null,
    val isError: String = ""
)