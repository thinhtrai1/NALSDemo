package com.nals.demo.data.home.local

import com.nals.demo.data.home.entities.Weather

interface HomeLocalDataSource {
    suspend fun insertWeather(weather: Weather)
    suspend fun updateWeather(weather: Weather)
    suspend fun getWeather(date: String): Weather?
}