package com.sailingweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sailingweather.data.WeatherDataSource
import com.sailingweather.model.WeatherConditions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(val conditions: WeatherConditions) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

class WeatherViewModel : ViewModel() {

    private val dataSource = WeatherDataSource()

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            dataSource.fetchConditions()
                .onSuccess { conditions ->
                    _uiState.value = WeatherUiState.Success(conditions)
                }
                .onFailure { error ->
                    _uiState.value = WeatherUiState.Error(
                        error.message ?: "Failed to fetch weather data"
                    )
                }
            _isRefreshing.value = false
        }
    }
}
