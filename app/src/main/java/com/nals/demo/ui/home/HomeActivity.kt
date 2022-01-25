package com.nals.demo.ui.home

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.nals.demo.R
import com.nals.demo.base.BaseActivity
import com.nals.demo.data.home.entities.Weather
import com.nals.demo.databinding.ActivityHomeBinding
import com.nals.demo.model.WeatherDay
import com.nals.demo.util.ApiResult
import com.nals.demo.util.extension.getFullImageUrl
import com.nals.demo.util.extension.isNetworkConnection
import com.nals.demo.util.extension.screenWidth
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.DecimalFormat
import java.util.*
import kotlin.math.abs

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()
    private val locationId = "1252431"
    private lateinit var weatherDays: List<WeatherDay>
    private lateinit var pagerAdapter: HomeDayPagerAdapter
    private var weatherIconAnim: ObjectAnimator? = null
    private val random = Random()

    override fun onViewCreated() {
        val currentDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        val firstCalendar = Calendar.getInstance().apply {
            add(Calendar.DATE, -8)
        }
        weatherDays = List(21) {
            firstCalendar.add(Calendar.DATE, 1)
            val dayName = if (firstCalendar[Calendar.DAY_OF_MONTH] == currentDay) {
                getString(R.string.today)
            } else {
                WeatherDay.getDisplayName(firstCalendar.timeInMillis)
            }
            WeatherDay(it, firstCalendar.timeInMillis, dayName)
        }
        with(mBinding) {
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                getData(weatherDays[pagerAdapter.currentSelected])
            }

            pagerAdapter = HomeDayPagerAdapter(this@HomeActivity, weatherDays) {
                updateViewSelected(pagerAdapter.currentSelected, it)
                getData(weatherDays[it])
                pagerAdapter.currentSelected = it
            }
            var currentPage = 1
            viewPager.adapter = pagerAdapter
            viewPager.currentItem = currentPage
            viewPager.offscreenPageLimit = 2
            viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    val newSelected = pagerAdapter.currentSelected + (position - currentPage) * 7
                    updateViewSelected(pagerAdapter.currentSelected, newSelected)
                    getData(weatherDays[newSelected])
                    pagerAdapter.currentSelected = newSelected
                    currentPage = position
                }
            })
        }

        getData(weatherDays[pagerAdapter.currentSelected])
    }

    override fun subscribeData() {
        viewModel.weather.observe(this) {
            showData(it)
        }
        viewModel.loading.observe(this) {
            showLoading(it)
        }
        viewModel.error.observe(this) {
            val message = when (it) {
                is ApiResult.ErrorType.EMPTY -> {
                    mBinding.tvNoData.isVisible = true
                    return@observe
                }
                is ApiResult.ErrorType.NETWORK -> {
                    mBinding.tvNoData.isVisible = false
                    it.exception.message ?: getString(R.string.an_error_occurred)
                }
                else -> {
                    mBinding.tvNoData.isVisible = false
                    getString(R.string.an_error_occurred)
                }
            }
            Snackbar.make(mBinding.swipeRefreshLayout, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getData(weatherDay: WeatherDay) {
        mBinding.weather = null
        if (isNetworkConnection()) {
            val calendar = Calendar.getInstance().apply { timeInMillis = weatherDay.time }
            viewModel.getWeather(
                locationId,
                calendar[Calendar.YEAR].toString(),
                (calendar[Calendar.MONTH] + 1).toString(),
                calendar[Calendar.DAY_OF_MONTH].toString(),
            )
        } else {
            viewModel.getWeather(weatherDay.getQuery())
        }
    }

    private fun showData(data: Weather) {
        with(mBinding) {
            weather = data
            tvNoData.isVisible = false
            tvDate.text = weatherDays[pagerAdapter.currentSelected].getDisplayFullDay()
            progressViewHumidity.setProgress(data.humidity)
            progressViewPredictability.setProgress(data.predictability)
            tvHumidity.setAnimateText(data.humidity)
            tvPredictability.setAnimateText(data.predictability)
            tvTemperature.setAnimateText(data.theTemp) {
                getString(R.string.number_temperature, DecimalFormat("#.#").format(it))
            }
            Picasso.get().load(getFullImageUrl(data.weatherStateAbbr)).into(imvWeather, object : Callback {
                override fun onSuccess() {
                    imvWeather.startAnimation(
                        AnimationUtils.loadAnimation(this@HomeActivity, R.anim.anim_weather_icon_slide_down_alpha)
                    )
                    if (data.weatherStateAbbr == "c") {
                        weatherIconAnim?.cancel()
                        weatherIconAnim = ObjectAnimator.ofFloat(imvWeather, "rotation", 0F, 360F).apply {
                            duration = 30000
                            repeatCount = ObjectAnimator.INFINITE
                            interpolator = LinearInterpolator()
                            start()
                        }
                        imvWeather.translationX = 0f
                    } else {
                        weatherIconAnim?.cancel()
                        imvWeather.rotation = 0f
                        randomAnimateCloud()
                    }
                }

                override fun onError(e: Exception?) {
                }
            })
        }
    }

    private fun randomAnimateCloud() {
        val initialX = screenWidth / 2f - resources.getDimensionPixelSize(R.dimen.home_weather_icon) / 2
        val targetX = random.nextInt(screenWidth).toFloat()
        weatherIconAnim = ObjectAnimator.ofFloat(mBinding.imvWeather, "x", initialX, targetX).apply {
            interpolator = LinearInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            duration = (abs(targetX - initialX) * 100).toLong()
            start()
        }
    }

    private fun updateViewSelected(oldPosition: Int, newPosition: Int) {
        mBinding.viewPager.findViewWithTag<View>(oldPosition)?.isSelected = false
        mBinding.viewPager.findViewWithTag<View>(newPosition)?.isSelected = true
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherIconAnim?.cancel()
    }
}