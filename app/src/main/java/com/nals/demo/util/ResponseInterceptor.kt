package com.nals.demo.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ResponseInterceptor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.proceed(chain.request()).let {
            return it
        }
    }
}