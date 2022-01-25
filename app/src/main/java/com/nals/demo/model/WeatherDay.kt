package com.nals.demo.model

import java.text.SimpleDateFormat
import java.util.*

class WeatherDay(
    val position: Int,
    val time: Long,
    val name: String
) {
    fun getDisplayDay(): String = SimpleDateFormat("M/dd", Locale.US).format(Date(time))
    fun getDisplayFullDay(): String = SimpleDateFormat("EEE MMM d, yyyy", Locale.US).format(Date(time))

    fun getQuery(): String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date(time))

    companion object {
        fun getDisplayName(time: Long): String = SimpleDateFormat("EEE", Locale.US).format(Date(time))
    }
}