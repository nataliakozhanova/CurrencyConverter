package com.example.currencyconverter.data.network

import android.content.Context
import android.util.Log
import com.example.currencyconverter.data.ResponseBase
import com.example.currencyconverter.data.dto.CurrenciesCatalogRequest
import com.example.currencyconverter.data.dto.CurrenciesCatalogResponse
import com.example.currencyconverter.domain.models.NoInternetError
import com.example.currencyconverter.domain.models.NotFoundError
import com.example.currencyconverter.domain.models.ServerInternalError
import com.example.currencyconverter.util.isConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class RetrofitNetworkClient(
    private val apiService: FrankfurterApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): ResponseBase {
        if (!isConnected(context)) {
            return ResponseBase(NoInternetError())
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is CurrenciesCatalogRequest -> {
                        val response = apiService.getAllCurrencies()
                        resultByResponseCode(response.code(), convertCurrenciesCatalogResponse(response.body()))
                    }
                    else -> ResponseBase(ServerInternalError())
                }

            } catch (e: HttpException) {
                e.message?.let { Log.e("Http", it) }
                ResponseBase(ServerInternalError())
            }
            // выполняется, если в эмуляторе интернет смартфона включен, а на компьютере интернет отпал
            catch (e: IOException) {
                e.message?.let { Log.e("IO", it) }
                ResponseBase(ServerInternalError())
            }
        }
    }

    private fun resultByResponseCode(code: Int, okResult: ResponseBase): ResponseBase {
        return when (code) {
            HttpURLConnection.HTTP_OK -> okResult
            HttpURLConnection.HTTP_NOT_FOUND -> ResponseBase(NotFoundError())
            else -> ResponseBase(ServerInternalError())
        }
    }

    private fun convertCurrenciesCatalogResponse(responseBody: Map<String, String>?): ResponseBase =
        if (responseBody == null) {
            ResponseBase(NotFoundError())
        } else {
            CurrenciesCatalogResponse(
                responseBody.keys.toList()
            )
        }
}