package com.nals.demo.util

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nals.demo.view.CustomProgressView
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min

class CustomTouch : View.OnTouchListener {
    private var fingerRotation = 0f
    private var viewRotation = 0f
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var rotateAnimator: ObjectAnimator? = null
    private var view: View? = null
    private var scaleDetector: ScaleGestureDetector? = null
    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            view?.apply {
                max(0.5f, min(scaleX * detector.scaleFactor, 3.0f)).let {
                    scaleX = it
                    scaleY = it
                }
            }
            return true
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val xc: Float = v.width / 2f
        val yc: Float = xc

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                viewRotation = v.rotation
                fingerRotation = Math.toDegrees(atan2((x - xc).toDouble(), (yc - y).toDouble())).toFloat()
                swipeRefreshLayout?.isEnabled = false
            }
            MotionEvent.ACTION_MOVE -> {
                viewRotation += Math.toDegrees(atan2((x - xc).toDouble(), (yc - y).toDouble())).toFloat() - fingerRotation
                if (viewRotation > 360) viewRotation -= 360
                else if (viewRotation < 0) viewRotation = 360 - viewRotation
                v.rotation = viewRotation
            }
            MotionEvent.ACTION_UP -> {
                fingerRotation = 0f
                swipeRefreshLayout?.isEnabled = true
                rotateAnimator?.cancel()
                rotateAnimator = ObjectAnimator.ofFloat(v, "rotation", if (viewRotation > 180) viewRotation - 360 else viewRotation, 0f).apply {
                    interpolator = OvershootInterpolator(2f)
                    duration = CustomProgressView.ANIMATION_DURATION
                    start()
                }
            }
        }
        if (scaleDetector == null) {
            scaleDetector = ScaleGestureDetector(v.context, scaleListener)
            view = v
        }
        scaleDetector!!.onTouchEvent(event)
        return true
    }

    fun withSwipeLayout(view: SwipeRefreshLayout): CustomTouch {
        swipeRefreshLayout = view
        return this
    }
}