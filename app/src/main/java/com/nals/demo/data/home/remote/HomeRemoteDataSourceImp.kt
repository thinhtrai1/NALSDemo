package com.nals.demo.data.home.remote

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiService
import retrofit2.Response
import javax.inject.Inject

class HomeRemoteDataSourceImp @Inject constructor(private val apiService: ApiService): HomeRemoteDataSource {
    override suspend fun getWeathers(id: String, year: String, month: String, day: String): Response<List<Weather>> {
        return apiService.getWeathers(id, year, month, day)
    }
}
