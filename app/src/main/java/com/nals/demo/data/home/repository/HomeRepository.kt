package com.nals.demo.data.home.repository

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult

interface HomeRepository {
    suspend fun getWeather(id: String, year: String, month: String, day: String): ApiResult<Weather>
    suspend fun insertWeather(weather: Weather)
    suspend fun updateWeather(weather: Weather)
    suspend fun getWeather(date: String): Weather?
}