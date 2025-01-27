package com.example.currencyconverter.data.network

import com.example.currencyconverter.domain.models.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FrankfurterApiService {
    @GET("/v1/currencies")
    suspend fun getAllCurrencies() : Response<Map<String, String>>

    @GET("/v1/{date}")
    suspend fun getExchangeRate(
        @Path("date") date: String, // Дата в формате "YYYY-MM-DD"
        @Query("base") base: String, // Базовая валюта
        @Query("symbols") symbols: List<String> // Валюта для конверсии
    ): Response<ExchangeRateResponse>
}