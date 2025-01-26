package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.dataModule
import com.example.currencyconverter.di.interactorModule
import com.example.currencyconverter.di.repositoryModule
import com.example.currencyconverter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, viewModelModule, interactorModule, repositoryModule)
        }
    }
}