package com.nals.demo.util

import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val type: ErrorType) : ApiResult<Nothing>()

    companion object {
        suspend fun <T> getResult(action: suspend () -> Response<T>): ApiResult<T> {
            try {
                action()
            } catch (e: Exception) {
                return Error(ErrorType.NETWORK(e))
            }.run {
                return if (isSuccessful && body() != null) {
                    Success(body()!!)
                } else {
                    Error(ErrorType.NETWORK(Exception(message())))
                }
            }
        }
    }

    sealed class ErrorType {
        data class NETWORK(val exception: Exception): ErrorType()
        object EMPTY: ErrorType()
        object UNKNOWN: ErrorType()
    }
}