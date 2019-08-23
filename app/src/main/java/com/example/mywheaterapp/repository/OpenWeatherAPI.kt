package com.example.mywheaterapp.repository

import com.example.mywheaterapp.repository.model.AllResponse
import com.example.mywheaterapp.repository.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {

    @GET("forecast/daily/")
    fun dailyForecast(@Query("q") cityName : String, @Query("cnt") dayCount : Int) : Call<WeatherResponse>

    @GET("weather?q=Yerevan&APPID=30eca0dbe8b08e3be7d5742804abfee3")
    fun getYerevanForecast() : Call<AllResponse>

    companion object {
        val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }

}