package com.nals.demo.ui.home

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.nals.demo.R
import com.nals.demo.model.WeatherDay

class HomeDayPagerAdapter(
    private val context: Context,
    private val list: List<WeatherDay>,
    private val listener: IOnItemSelectedListener
) : PagerAdapter() {

    var currentSelected = 7

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LinearLayout(context).apply {
            for (i in position * 7..position * 7 + 6) {
                val weatherDay = list[i]
                addView(
                    ItemView(context, weatherDay).apply {
                        isSelected = currentSelected == weatherDay.position
                        tag = weatherDay.position
                        setOnClickListener {
                            listener.onItemSelected(weatherDay.position)
                        }
                    }
                )
            }
            container.addView(this)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

    private class ItemView(context: Context, weatherDay: WeatherDay): LinearLayout(context) {
        init {
            orientation = VERTICAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                weight = 1f
            }
            setBackgroundResource(R.drawable.bg_item_rcv_weather_day)
            setPadding(0, resources.getDimensionPixelSize(R.dimen.margin_8), 0, resources.getDimensionPixelSize(R.dimen.margin_4))
            addView(
                TextView(context).apply {
                    typeface = Typeface.DEFAULT_BOLD
                    text = weatherDay.name
                    gravity = Gravity.CENTER
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_15))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            )
            addView(
                TextView(context).apply {
                    typeface = Typeface.DEFAULT_BOLD
                    text = weatherDay.getDisplayDay()
                    gravity = Gravity.CENTER
                    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                        weight = 1f
                    }
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_13))
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            )
        }
    }

    fun interface IOnItemSelectedListener {
        fun onItemSelected(position: Int)
    }
}