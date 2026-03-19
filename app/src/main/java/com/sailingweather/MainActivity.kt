package com.sailingweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.sailingweather.data.Analytics
import com.sailingweather.ui.screens.WeatherScreen
import com.sailingweather.ui.theme.SailingWeatherTheme
import com.sailingweather.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytics.init(FirebaseAnalytics.getInstance(this))
        enableEdgeToEdge()
        setContent {
            SailingWeatherTheme {
                val isDark = isSystemInDarkTheme()
                LaunchedEffect(isDark) { Analytics.logTheme(isDark) }

                val weatherViewModel: WeatherViewModel = viewModel()
                WeatherScreen(viewModel = weatherViewModel)
            }
        }
    }
}
