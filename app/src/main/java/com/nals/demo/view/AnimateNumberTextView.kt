package com.nals.demo.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class AnimateNumberTextView(context: Context, attrs: AttributeSet): TextView(context, attrs) {
    private var animation: ValueAnimator? = null

    fun setAnimateText(input: Int) {
        animation?.cancel()
        animation = ValueAnimator.ofInt(0, input).apply {
            duration = CustomProgressView.ANIMATION_DURATION
            addUpdateListener { animation ->
                text = animation.animatedValue.toString()
            }
            start()
        }
    }

    fun setAnimateText(input: Float, onUpdateListener: (Float) -> String) {
        animation?.cancel()
        animation = ValueAnimator.ofFloat(0f, input).apply {
            duration = CustomProgressView.ANIMATION_DURATION
            addUpdateListener {
                text = onUpdateListener(it.animatedValue as Float)
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animation?.cancel()
    }
}