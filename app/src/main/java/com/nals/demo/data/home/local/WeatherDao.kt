package com.nals.demo.data.home.local

import androidx.room.*
import com.nals.demo.data.home.entities.Weather
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather)

    @Update
    fun updateWeather(weather: Weather)

    @Query("SELECT * FROM weathers WHERE applicableDate LIKE :date LIMIT 1")
    fun getWeather(date: String): Single<Weather>
}