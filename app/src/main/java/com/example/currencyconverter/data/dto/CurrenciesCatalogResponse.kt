package com.example.currencyconverter.data.dto

import com.example.currencyconverter.data.ResponseBase

data class CurrenciesCatalogResponse(
    val areasCatalog: List<String>
) : ResponseBase()