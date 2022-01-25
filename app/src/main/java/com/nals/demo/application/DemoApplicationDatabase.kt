package com.nals.demo.application

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nals.demo.data.home.local.WeatherDao
import com.nals.demo.data.home.entities.Weather

@Database(entities = [Weather::class], version = 1, exportSchema = false)
abstract class DemoApplicationDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}