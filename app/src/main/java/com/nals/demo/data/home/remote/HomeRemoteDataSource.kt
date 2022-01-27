package com.nals.demo.data.home.remote

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult
import io.reactivex.Observable

interface HomeRemoteDataSource {
    fun getWeathers(id: String, year: String, month: String, day: String): Observable<List<Weather>>
}