package com.company.jetweatherforecast.di

import android.content.Context
import androidx.room.Room
import com.company.jetweatherforecast.dataLayer.data.WeatherDao
import com.company.jetweatherforecast.dataLayer.data.WeatherDatabase
import com.company.jetweatherforecast.dataLayer.network.WeatherApi
import com.company.jetweatherforecast.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOpenWeatherApi() : WeatherApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context) :
            WeatherDatabase = Room.databaseBuilder(
        applicationContext,
        WeatherDatabase::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()

}