package com.nals.demo.data.home.local

import androidx.room.*
import com.nals.demo.data.home.entities.Weather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Update
    suspend fun updateWeather(weather: Weather)

    @Query("SELECT * FROM weathers WHERE applicableDate LIKE :date LIMIT 1")
    suspend fun getWeather(date: String): Weather?
}