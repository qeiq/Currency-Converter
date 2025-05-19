package com.imam.currencyconverter.domain.repository

import com.imam.currencyconverter.domain.model.CurrencyRate
import com.imam.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getRates(base: String): Flow<Resource<CurrencyRate>>


}