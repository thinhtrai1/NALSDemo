package com.nals.demo.data.home.remote

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult
import com.nals.demo.util.ApiService
import javax.inject.Inject

class HomeRemoteDataSourceImp @Inject constructor(private val apiService: ApiService): HomeRemoteDataSource {
    override suspend fun getWeathers(id: String, year: String, month: String, day: String): ApiResult<List<Weather>> {
        return ApiResult.getResult {
            apiService.getWeathers(id, year, month, day)
        }
    }
}
