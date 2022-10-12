package com.company.jetweatherforecast.domainLayer.components

import androidx.lifecycle.ViewModel
import com.company.jetweatherforecast.dataLayer.data.DataOrException
import com.company.jetweatherforecast.dataLayer.model.Weather
import com.company.jetweatherforecast.dataLayer.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository)
    : ViewModel(){

    suspend fun getWeatherData(city: String, unit: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, unit = unit)
    }


}