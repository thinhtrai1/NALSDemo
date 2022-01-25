package com.nals.demo.data.home.repository

import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult

import org.junit.Test
import org.junit.Assert.*

class HomeRepositoryImpTest {

    @Test
    fun getWeather() {
        val result = ApiResult.Success(
            listOf(
                Weather(
                    weatherId = 0,
                    weatherStateName = "Heavy Cloud",
                    weatherStateAbbr = "hc",
                    applicableDate = "2022-02-01",
                    theTemp = 33.43f,
                    humidity = 58,
                    predictability = 71
                ),
                Weather(
                    weatherId = 0,
                    weatherStateName = "Clear",
                    weatherStateAbbr = "c",
                    applicableDate = "2022-02-01",
                    theTemp = 36f,
                    humidity = 50,
                    predictability = 73
                ),
                Weather(
                    weatherId = 0,
                    weatherStateName = "Heavy Cloud",
                    weatherStateAbbr = "hc",
                    applicableDate = "2022-02-01",
                    theTemp = 33.43f,
                    humidity = 58,
                    predictability = 71
                ),
            )
        )
        result.data.maxByOrNull { it.weatherStateAbbr }?.let { weather ->
            val humidity = result.data.map { it.humidity }.average().toInt()
            val predictability = result.data.map { it.predictability }.average().toInt()
            assertEquals(humidity, (result.data[0].humidity + result.data[1].humidity + result.data[2].humidity) / 3)
            assertEquals(predictability, (result.data[0].predictability + result.data[1].predictability + result.data[2].predictability) / 3)
            assertEquals(weather.weatherStateAbbr, "hc")
        } ?: assert(false)
    }
}