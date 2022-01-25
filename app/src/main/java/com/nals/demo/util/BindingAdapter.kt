package com.nals.demo.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.nals.demo.util.extension.getFullImageUrl
import com.squareup.picasso.Picasso

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:visible")
    fun View.setVisible(isVisible: Boolean) {
        visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:imagePathUrl")
    fun ImageView.setImagePathUrl(path: String?) {
        path?.let {
            Picasso.get().load(getFullImageUrl(it)).into(this)
        }
    }
}