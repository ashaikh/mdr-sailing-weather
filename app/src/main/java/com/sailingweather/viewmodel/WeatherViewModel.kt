package com.sailingweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailingweather.data.ForecastDataSource
import com.sailingweather.data.WeatherDataSource
import com.sailingweather.model.ForecastPeriod
import com.sailingweather.model.WeatherConditions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(
        val conditions: WeatherConditions,
        val forecast: List<ForecastPeriod> = emptyList()
    ) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

class WeatherViewModel : ViewModel() {

    private val dataSource = WeatherDataSource()
    private val forecastSource = ForecastDataSource()

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        refresh()
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                delay(10 * 60 * 1000L) // 10 minutes, matches station update interval
                fetchData()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchData()
            _isRefreshing.value = false
        }
    }

    private suspend fun fetchData() {
        dataSource.fetchConditions()
            .onSuccess { conditions ->
                val forecast = forecastSource.fetchForecast().getOrDefault(emptyList())
                _uiState.value = WeatherUiState.Success(conditions, forecast)
            }
            .onFailure { error ->
                _uiState.value = WeatherUiState.Error(
                    error.message ?: "Failed to fetch weather data"
                )
            }
    }
}
