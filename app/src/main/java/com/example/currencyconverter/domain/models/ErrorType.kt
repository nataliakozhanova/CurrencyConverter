package com.example.currencyconverter.domain.models

abstract class ErrorType
class Success : ErrorType()
class ServerInternalError : ErrorType()
class NoInternetError : ErrorType()
class NotFoundError : ErrorType()