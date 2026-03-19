package com.sailingweather.model

data class ForecastPeriod(
    val name: String,            // "Tonight", "Thursday", "Thursday Night"
    val temperature: Int,        // degrees
    val temperatureUnit: String, // "F"
    val windSpeed: String,       // "5 to 10 mph"
    val windDirection: String,   // "SSW"
    val shortForecast: String,   // "Sunny"
    val detailedForecast: String // full description
)
