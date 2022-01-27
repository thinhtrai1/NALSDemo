package com.nals.demo.data.home.repository

import com.nals.demo.data.home.local.HomeLocalDataSource
import com.nals.demo.data.home.remote.HomeRemoteDataSource
import com.nals.demo.data.home.entities.Weather
import com.nals.demo.util.ApiResult
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class HomeRepositoryImp(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override fun getWeather(id: String, year: String, month: String, day: String): Observable<ApiResult<Weather>> {
        return remoteDataSource.getWeathers(id, year, month, day)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .map { data ->
                data.maxByOrNull { it.weatherStateAbbr }?.let { weather ->
                    weather.humidity = data.map { it.humidity }.average().toInt()
                    weather.predictability = data.map { it.predictability }.average().toInt()
                    getWeather(weather.applicableDate)
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ localWeather ->
                            updateWeather(weather.copy(weatherId = localWeather.weatherId))
                        }, {
                            insertWeather(weather)
                        }).addTo(CompositeDisposable())
                    ApiResult.Success(weather)
                } ?: ApiResult.Error(ApiResult.ErrorType.EMPTY)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                ApiResult.Error(ApiResult.ErrorType.NETWORK(it))
            }
    }

    override fun insertWeather(weather: Weather) {
        localDataSource.insertWeather(weather)
    }

    override fun updateWeather(weather: Weather) {
        localDataSource.updateWeather(weather)
    }

    override fun getWeather(date: String): Single<Weather> {
        return localDataSource.getWeather(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}