package com.example.mywheaterapp

import com.example.mywheaterapp.repository.OpenWeatherAPI
import com.example.mywheaterapp.repository.model.*
import com.example.mywheaterapp.util.ErrorTypes
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter(val view : MainView) {
    @Inject
    lateinit var api : OpenWeatherAPI

    fun getForecastForSevenDays(cityName : String) {

        view.showSpinner()
        api.dailyForecast(cityName , 7).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                response.body()?.let {
                    //This method will be created in the next step.
                    createListForView(it)
                    view.hideSpinner()
                } ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable) {
                view.showErrorToast(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }


    private fun  createListForView(weatherResponse: WeatherResponse) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for (forecastDetail : ForecastDetail in weatherResponse.forecast) {
            val dayTemp = forecastDetail.temperature.dayTemperature
            val forecastItem = ForecastItemViewModel(degreeDay = dayTemp.toString(),
                date = forecastDetail.date,
                icon = forecastDetail.description[0].icon,
                description = forecastDetail.description[0].description)
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }


    fun getYerevanForecast() {

        view.showSpinner()
        api.getYerevanForecast().enqueue(object : Callback<AllResponse> {

            override fun onResponse(call: Call<AllResponse>, response: Response<AllResponse>) {
                response.body()?.let {

                    view.setYerevanForecast(it)
                    view.hideSpinner()
                } ?: view.showErrorToast(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<AllResponse>?, t: Throwable) {
                view.showErrorToast(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })

    }
}