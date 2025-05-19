package com.imam.currencyconverter.data.remote

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("npm/@fawazahmed0/currency-api@latest/v1/currencies/{base}.json")
    suspend fun getRates(@Path("base") base: String): JsonObject


}