package com.example.currencyconverter.domain.api

import kotlinx.coroutines.flow.Flow

interface CurConvRepository {
    fun getAllCurrencies(): Flow<List<String>>
}