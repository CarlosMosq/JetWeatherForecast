package com.company.jetweatherforecast.dataLayer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.company.jetweatherforecast.dataLayer.model.Favorite
import com.company.jetweatherforecast.dataLayer.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}