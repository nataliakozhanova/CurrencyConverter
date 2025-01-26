package com.example.currencyconverter.domain.api

import kotlinx.coroutines.flow.Flow

interface CurConvInteractor {
    fun getAllCurrencies(): Flow<List<String>>
}