package com.nals.demo.base

import android.app.Dialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(private val contentLayoutId: Int) : AppCompatActivity() {
    protected lateinit var mBinding: T
    private lateinit var mProgressDialog: Dialog

    abstract fun onViewCreated()
    abstract fun subscribeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, contentLayoutId)
        mBinding.lifecycleOwner = this
        mProgressDialog = Dialog(this).apply {
            setContentView(ProgressBar(this@BaseActivity))
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        onViewCreated()
        subscribeData()
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            mProgressDialog.show()
        } else {
            mProgressDialog.dismiss()
        }
    }
}