package com.nals.demo.util

import com.nals.demo.data.home.entities.Weather
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("location/{id}/{year}/{month}/{day}")
    suspend fun getWeathers(
        @Path("id") id: String,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String,
    ): Response<List<Weather>>
}