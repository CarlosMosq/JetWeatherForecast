package com.company.jetweatherforecast.dataLayer.network

import com.company.jetweatherforecast.dataLayer.model.Weather
import com.company.jetweatherforecast.utils.Constants.COURSE_API_KEY
import com.company.jetweatherforecast.utils.Constants.TARGET_URL
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = TARGET_URL)
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = COURSE_API_KEY
    ): Weather
}