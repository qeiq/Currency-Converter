package com.imam.currencyconverter.presentation.home_screen

data class HomeUiState(
    val fromCountryCode: String = "us",
    val fromCurrencyCode: String = "usd",
    val toCountryCode: String = "jp",
    val toCurrencyCode: String = "jpy",
    val currentAmount: String = "",
    val convertedAmount: Double = 0.0,
    val searchQuery: String = ""
)
