package com.company.jetweatherforecast.dataLayer.repository

import android.util.Log
import com.company.jetweatherforecast.dataLayer.data.DataOrException
import com.company.jetweatherforecast.dataLayer.model.Weather
import com.company.jetweatherforecast.dataLayer.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String, unit: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = unit)
        } catch (e: Exception) {
            Log.d("REX", "getWeather $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather $response")
        return DataOrException(data = response)
    }
}