package com.nals.demo.data.home.local

import com.nals.demo.data.home.entities.Weather
import io.reactivex.Single

interface HomeLocalDataSource {
    fun insertWeather(weather: Weather)
    fun updateWeather(weather: Weather)
    fun getWeather(date: String): Single<Weather>
}