package com.imam.currencyconverter.domain.model

data class CurrencyRate(
    val base: String,
    val rates: Map<String, Double>
)
