package com.imam.currencyconverter.domain.usecase

import com.imam.currencyconverter.domain.model.CurrencyRate
import com.imam.currencyconverter.domain.repository.AppRepository
import com.imam.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetRatesUseCase(
    private val repository: AppRepository
) {
    operator fun invoke(base: String): Flow<Resource<CurrencyRate>> {
        return repository.getRates(base)
    }
}