package com.example.mywheaterapp.repository.model

import com.example.mywheaterapp.repository.model.City
import com.google.gson.annotations.SerializedName

data class WeatherResponse (@SerializedName("city") var city : City,
                            @SerializedName("list") var forecast : List<ForecastDetail>)