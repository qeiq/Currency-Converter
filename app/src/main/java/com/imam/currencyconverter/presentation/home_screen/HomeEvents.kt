package com.imam.currencyconverter.presentation.home_screen

sealed class HomeEvents {
    data class FromCountrySelected(val countryCode: String, val currencyCode: String) : HomeEvents()
    data class ToCountrySelected(val countryCode: String, val currencyCode: String) : HomeEvents()
    data class CurrencyAmount(val amount: String) : HomeEvents()
    data class SearchQueryChanged(val query: String) : HomeEvents()
    object SwapCurrencies : HomeEvents()
}


