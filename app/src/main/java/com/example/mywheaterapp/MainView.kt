package com.example.mywheaterapp

import com.example.mywheaterapp.repository.model.ForecastItemViewModel

import com.example.mywheaterapp.repository.model.AllResponse
import com.example.mywheaterapp.util.ErrorTypes

interface MainView {
    fun showSpinner()
    fun hideSpinner()
    fun updateForecast(forecasts: List<ForecastItemViewModel>)
    fun setYerevanForecast(main: AllResponse)
    fun showErrorToast(errorType: ErrorTypes)
    fun showToast(toast:String)
}