package com.example.currencyconverter.di

import com.example.currencyconverter.data.network.FrankfurterApiService
import com.example.currencyconverter.data.network.NetworkClient
import com.example.currencyconverter.data.network.RetrofitNetworkClient
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_BASE_URL = "https://api.frankfurter.dev"

val dataModule = module {
    factory { Gson() }

    single<FrankfurterApiService> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FrankfurterApiService::class.java)
    }

    single<NetworkClient>() {
        RetrofitNetworkClient(get(), androidContext())
    }
}