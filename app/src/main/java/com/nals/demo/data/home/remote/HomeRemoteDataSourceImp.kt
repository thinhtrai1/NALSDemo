package com.nals.demo.data.home.remote

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult
import com.nals.demo.util.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class HomeRemoteDataSourceImp @Inject constructor(private val apiService: ApiService): HomeRemoteDataSource {
    override fun getWeathers(id: String, year: String, month: String, day: String): Observable<List<Weather>> {
        return apiService.getWeathers(id, year, month, day)
    }
}
