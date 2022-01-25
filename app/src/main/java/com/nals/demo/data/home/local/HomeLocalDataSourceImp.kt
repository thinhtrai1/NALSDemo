package com.nals.demo.data.home.local

import com.nals.demo.data.home.entities.Weather
import javax.inject.Inject

class HomeLocalDataSourceImp @Inject constructor(private val weatherDao: WeatherDao): HomeLocalDataSource {
    override suspend fun insertWeather(weather: Weather) {
        return weatherDao.insertWeather(weather)
    }

    override suspend fun updateWeather(weather: Weather) {
        weatherDao.updateWeather(weather)
    }

    override suspend fun getWeather(date: String): Weather? {
        return weatherDao.getWeather(date)
    }
}