package com.nals.demo.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.nals.demo.util.extension.toPx

class CustomProgressView(context: Context, attr: AttributeSet): View(context, attr) {
    private var progressAnimator: ValueAnimator? = null
    private val rectF = RectF()
    private var sweep = 0f
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.WHITE
    }
    private val borderPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#80FFFFFF")
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 4.toPx()
    }
    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10.toPx()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val padding = 5.toPx()
        val size = MeasureSpec.getSize(widthMeasureSpec) - padding
        rectF.set(padding, padding, size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.right / 2 - 15.toPx(), backgroundPaint)
        canvas.drawArc(rectF, 135f, 270f, false, borderPaint)
        canvas.drawArc(rectF, 135f, sweep, false, progressPaint)
    }

    fun setProgress(progress: Int) {
        progressAnimator?.cancel()
        progressAnimator = ValueAnimator.ofInt(0, progress).apply {
            duration = ANIMATION_DURATION
            addUpdateListener { animation ->
                sweep = animation.animatedValue as Int / 100f * 270
                invalidate()
            }
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        progressAnimator?.cancel()
    }

    companion object {
        const val ANIMATION_DURATION = 700L
    }
}