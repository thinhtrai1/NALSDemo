package com.nals.demo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nals.demo.base.BaseViewModel
import com.nals.demo.data.home.entities.Weather
import com.nals.demo.data.home.repository.HomeRepository
import com.nals.demo.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : BaseViewModel() {
    val weather: MutableLiveData<Weather> = MutableLiveData()

    fun getWeather(id: String, year: String, month: String, day: String) {
        viewModelScope.launch {
            loading.value = true
            when (val result = withContext(Dispatchers.IO) {
                homeRepository.getWeather(id, year, month, day)
            }) {
                is ApiResult.Success -> {
                    weather.value = result.data
                }
                is ApiResult.Error -> {
                    error.value = result.type
                }
            }
            loading.value = false
        }
    }

    fun getWeather(date: String) {
        viewModelScope.launch {
            val result = homeRepository.getWeather(date)
            if (result != null) {
                weather.value = result
            } else {
                error.value = ApiResult.ErrorType.EMPTY
            }
        }
    }
}