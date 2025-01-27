package com.example.currencyconverter.domain.models

data class ExchangeRateResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
