package com.nals.demo.data.home.remote

import com.nals.demo.data.home.entities.Weather
import retrofit2.Response

interface HomeRemoteDataSource {
    suspend fun getWeathers(id: String, year: String, month: String, day: String): Response<List<Weather>>
}