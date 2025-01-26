package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.ResponseBase

interface NetworkClient {
    suspend fun doRequest(dto: Any): ResponseBase
}