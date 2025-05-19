package com.imam.currencyconverter.data.repository

import com.imam.currencyconverter.data.remote.ApiInterface
import com.imam.currencyconverter.domain.model.CurrencyRate
import com.imam.currencyconverter.domain.repository.AppRepository
import com.imam.currencyconverter.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImp(private val apiInterface: ApiInterface) : AppRepository {
    override fun getRates(base: String): Flow<Resource<CurrencyRate>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiInterface.getRates(base.lowercase())
            val ratesJson = response.getAsJsonObject(base.lowercase())

            val rates: Map<String, Double> = ratesJson.entrySet().associate {
                it.key to it.value.asDouble
            }

            emit(Resource.Success(CurrencyRate(base = base, rates = rates)))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load rates: ${e.localizedMessage}"))
        }
    }

}