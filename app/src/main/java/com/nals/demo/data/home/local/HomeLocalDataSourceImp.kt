package com.nals.demo.data.home.local

import com.nals.demo.data.home.entities.Weather
import io.reactivex.Single
import javax.inject.Inject

class HomeLocalDataSourceImp @Inject constructor(private val weatherDao: WeatherDao): HomeLocalDataSource {
    override fun insertWeather(weather: Weather) {
        return weatherDao.insertWeather(weather)
    }

    override fun updateWeather(weather: Weather) {
        weatherDao.updateWeather(weather)
    }

    override fun getWeather(date: String): Single<Weather> {
        return weatherDao.getWeather(date)
    }
}