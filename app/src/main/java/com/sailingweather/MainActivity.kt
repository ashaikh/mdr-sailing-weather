package com.sailingweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sailingweather.ui.screens.WeatherScreen
import com.sailingweather.ui.theme.SailingWeatherTheme
import com.sailingweather.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SailingWeatherTheme {
                val weatherViewModel: WeatherViewModel = viewModel()
                WeatherScreen(viewModel = weatherViewModel)
            }
        }
    }
}
