package com.nals.demo.data.home.repository

import com.nals.demo.data.home.local.HomeLocalDataSource
import com.nals.demo.data.home.remote.HomeRemoteDataSource
import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult

class HomeRepositoryImp(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getWeather(id: String, year: String, month: String, day: String): ApiResult<Weather> {
        when (val result = ApiResult.getResult {
            remoteDataSource.getWeathers(id, year, month, day)
        }) {
            is ApiResult.Success -> {
                result.data.maxByOrNull { it.weatherStateAbbr }?.let { weather ->
                    weather.humidity = result.data.map { it.humidity }.average().toInt()
                    weather.predictability = result.data.map { it.predictability }.average().toInt()
                    val localWeather = getWeather(weather.applicableDate)
                    if (localWeather != null) {
                        updateWeather(weather.copy(weatherId = localWeather.weatherId))
                    } else {
                        insertWeather(weather)
                    }
                    return ApiResult.Success(weather)
                }
                return ApiResult.Error(ApiResult.ErrorType.EMPTY)
            }
            is ApiResult.Error -> return ApiResult.Error(result.type)
            else -> return ApiResult.Error(ApiResult.ErrorType.UNKNOWN)
        }
    }

    override suspend fun insertWeather(weather: Weather) {
        localDataSource.insertWeather(weather)
    }

    override suspend fun updateWeather(weather: Weather) {
        localDataSource.updateWeather(weather)
    }

    override suspend fun getWeather(date: String): Weather? {
        return localDataSource.getWeather(date)
    }
}