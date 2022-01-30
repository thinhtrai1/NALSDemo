package com.nals.demo.view

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.nals.demo.R
import com.nals.demo.util.extension.toPx
import kotlin.math.atan2

class CustomProgressView(context: Context, attr: AttributeSet) : View(context, attr), View.OnTouchListener {
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
        val newSweep = (sweep + newRotation).let {
            if (it < 0) 270 + it else if (it > 270) it - 270 else it
        }
        canvas.drawArc(rectF, 135f, newSweep, false, progressPaint)
    }

    init {
        setOnTouchListener(this)
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

    private var fingerRotation = 0f
    private var newRotation = 0f
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val center = width / 2f

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                fingerRotation = Math.toDegrees(atan2((x - center).toDouble(), (center - y).toDouble())).toFloat().let {
                    if (it < 0) 360 + it else it
                }
                (context as Activity).findViewById<View>(R.id.swipeRefreshLayout).isEnabled = false
            }
            MotionEvent.ACTION_MOVE -> {
                newRotation = Math.toDegrees(atan2((x - center).toDouble(), (center - y).toDouble())).toFloat().let {
                    (it - fingerRotation) * 270 / 360
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                fingerRotation = 0f
                newRotation = 0f
                invalidate()
                (context as Activity).findViewById<View>(R.id.swipeRefreshLayout).isEnabled = true
            }
        }
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        progressAnimator?.cancel()
    }

    companion object {
        const val ANIMATION_DURATION = 700L
    }
}