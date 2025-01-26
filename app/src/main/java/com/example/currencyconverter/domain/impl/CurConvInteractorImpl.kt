package com.example.currencyconverter.domain.impl

import com.example.currencyconverter.domain.api.CurConvInteractor
import com.example.currencyconverter.domain.api.CurConvRepository
import kotlinx.coroutines.flow.Flow

class CurConvInteractorImpl(
    private val curConvRepository: CurConvRepository
) : CurConvInteractor {
    override fun getAllCurrencies(): Flow<List<String>> {
        return curConvRepository.getAllCurrencies()
    }
}