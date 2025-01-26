package com.example.currencyconverter.data

import com.example.currencyconverter.domain.models.ErrorType
import com.example.currencyconverter.domain.models.Success

open class ResponseBase(val errorType: ErrorType = Success())