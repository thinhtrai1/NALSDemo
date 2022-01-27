package com.nals.demo.data.home.repository

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult
import io.reactivex.Observable
import io.reactivex.Single

interface HomeRepository {
    fun getWeather(id: String, year: String, month: String, day: String): Observable<ApiResult<Weather>>
    fun insertWeather(weather: Weather)
    fun updateWeather(weather: Weather)
    fun getWeather(date: String): Single<Weather>
}