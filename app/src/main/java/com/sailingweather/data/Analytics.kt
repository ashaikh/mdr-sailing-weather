package com.sailingweather.data

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.sailingweather.model.ForecastPeriod
import com.sailingweather.model.WeatherConditions

object Analytics {

    private lateinit var firebase: FirebaseAnalytics

    fun init(firebaseAnalytics: FirebaseAnalytics) {
        firebase = firebaseAnalytics
    }

    fun logWeatherLoaded(conditions: WeatherConditions) {
        firebase.logEvent("weather_loaded") {
            param("wind_speed_kts", conditions.windSpeed)
            param("wind_direction", conditions.windDirection.toLong())
            param("wind_direction_compass", conditions.windDirectionCompass)
            param("wind_avg_10min", conditions.windAvg10Min)
            param("wind_high", conditions.windHigh)
            param("temperature_f", conditions.temperature)
            param("humidity_pct", conditions.humidity.toLong())
            param("barometer", conditions.barometer)
            param("barometer_trend", conditions.barometerTrend)
            param("moon_phase", conditions.moonPhase)
            param("is_dinghy_sailing", if (conditions.isDinghySailing) "yes" else "no")
        }
    }

    fun logRefresh(trigger: String) {
        firebase.logEvent("weather_refresh") {
            param("trigger", trigger) // "manual", "pull_to_refresh", "auto", "app_open"
        }
    }

    fun logWeatherError(error: String) {
        firebase.logEvent("weather_error") {
            param("error_message", error.take(100))
        }
    }

    fun logForecastLoaded(periods: List<ForecastPeriod>) {
        firebase.logEvent("forecast_loaded") {
            param("period_count", periods.size.toLong())
            if (periods.isNotEmpty()) {
                param("tonight_temp", periods[0].temperature.toLong())
                param("tonight_wind", periods[0].windSpeed)
                param("tonight_forecast", periods[0].shortForecast)
            }
            if (periods.size > 1) {
                param("tomorrow_temp", periods[1].temperature.toLong())
                param("tomorrow_wind", periods[1].windSpeed)
                param("tomorrow_forecast", periods[1].shortForecast)
            }
        }
    }

    fun logTheme(isDark: Boolean) {
        firebase.logEvent("app_theme") {
            param("mode", if (isDark) "dark" else "light")
        }
    }
}
