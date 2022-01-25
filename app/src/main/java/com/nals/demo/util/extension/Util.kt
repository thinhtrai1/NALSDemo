package com.nals.demo.util.extension

import android.content.res.Resources
import com.nals.demo.BuildConfig

fun getFullImageUrl(path: String): String {
    return BuildConfig.BASE_URL + "static/img/weather/png/" + path + ".png"
}

fun Int.toPx() = this * Resources.getSystem().displayMetrics.density

val screenWidth = Resources.getSystem().displayMetrics.widthPixels