package com.nals.demo.data.home.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weathers")
data class Weather constructor(
    @PrimaryKey(autoGenerate = true) var weatherId: Long,

    @SerializedName("weather_state_name")
    var weatherStateName: String,

    @SerializedName("weather_state_abbr")
    var weatherStateAbbr: String,

    @SerializedName("applicable_date")
    var applicableDate: String,

    @SerializedName("the_temp")
    var theTemp: Float,

    @SerializedName("humidity")
    var humidity: Int,

    @SerializedName("predictability")
    var predictability: Int,
)