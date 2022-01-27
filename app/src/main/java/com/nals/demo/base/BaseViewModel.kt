package com.nals.demo.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nals.demo.util.ApiResult
import com.nals.demo.util.EventLiveData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val error = EventLiveData<ApiResult.ErrorType>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}