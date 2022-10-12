package com.company.jetweatherforecast.dataLayer.repository

import com.company.jetweatherforecast.dataLayer.data.WeatherDao
import com.company.jetweatherforecast.dataLayer.model.Favorite
import com.company.jetweatherforecast.dataLayer.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorites(favorite: Favorite) = weatherDao.insertFavorite(favorite = favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite = favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun getFavById(city: String) = weatherDao.getFavById(city = city)

    fun getUnits() : Flow<List<Unit>> = weatherDao.getUnit()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit = unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit = unit)
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit = unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

}