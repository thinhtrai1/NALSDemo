package com.nals.demo.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val type: ErrorType) : ApiResult<Nothing>()

    companion object {
        fun <T> getResult(action: () -> Observable<T>, callback: (ApiResult<T>) -> Unit) {
            CompositeDisposable().add(
                action()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback(Success(it))
                        },
                        {
                            callback(Error(ErrorType.NETWORK(it)))
                        }
                    )
            )
//            try {
//                action()
//            } catch (e: Exception) {
//                return Error(ErrorType.NETWORK(e))
//            }.run {
//                return if (isSuccessful && body() != null) {
//                    Success(body()!!)
//                } else {
//                    Error(ErrorType.NETWORK(Exception(message())))
//                }
//            }
        }
    }

    sealed class ErrorType {
        data class NETWORK(val exception: Throwable) : ErrorType()
        object EMPTY : ErrorType()
        object UNKNOWN : ErrorType()
    }
}