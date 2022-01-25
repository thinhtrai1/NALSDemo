package com.nals.demo.data.home.remote

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult

interface HomeRemoteDataSource {
    suspend fun getWeathers(id: String, year: String, month: String, day: String): ApiResult<List<Weather>>
}