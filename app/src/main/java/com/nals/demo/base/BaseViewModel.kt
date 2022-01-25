package com.nals.demo.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nals.demo.util.ApiResult
import com.nals.demo.util.EventLiveData

abstract class BaseViewModel: ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val error = EventLiveData<ApiResult.ErrorType>()
}