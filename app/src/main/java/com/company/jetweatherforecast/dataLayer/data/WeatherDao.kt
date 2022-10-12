package com.company.jetweatherforecast.dataLayer.data

import androidx.room.*
import com.company.jetweatherforecast.dataLayer.model.Favorite
import com.company.jetweatherforecast.dataLayer.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao  {
    @Query("SELECT * from fav_tbl")
    fun getFavorites() : Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city: String) : Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * from settings_tbl")
    fun getUnit() : Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Delete
    suspend fun deleteUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()
}