package com.nals.demo.ui.home

import androidx.lifecycle.MutableLiveData
import com.nals.demo.base.BaseViewModel
import com.nals.demo.data.home.entities.Weather
import com.nals.demo.data.home.repository.HomeRepository
import com.nals.demo.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : BaseViewModel() {
    val weather: MutableLiveData<Weather> = MutableLiveData()

    fun getWeather(id: String, year: String, month: String, day: String) {
        loading.value = true
        homeRepository.getWeather(id, year, month, day).subscribe { result ->
            when (result) {
                is ApiResult.Success -> {
                    weather.value = result.data
                }
                is ApiResult.Error -> {
                    error.value = result.type
                }
            }
            loading.value = false
        }.addTo(compositeDisposable)
    }

    fun getWeather(date: String) {
        homeRepository.getWeather(date).subscribe({ result ->
            weather.value = result
        }, {
            error.value = ApiResult.ErrorType.EMPTY
        }).addTo(compositeDisposable)
    }
}