package com.sailingweather.model

data class WeatherConditions(
    val timestamp: String,           // "8:41p 03/18/26"
    val windSpeed: Double,           // knots
    val windDirection: Int,          // degrees (0-360)
    val windAvg10Min: Double,        // knots
    val windHigh: Double,            // knots
    val windHighTime: String,        // "1:34p"
    val temperature: Double,         // fahrenheit
    val humidity: Int,               // percentage
    val barometer: Double,           // inches Hg
    val barometerTrend: String,      // "Rising Slowly"
    val sunrise: String,             // "6:58a"
    val sunset: String,              // "7:03p"
    val moonPhase: String,           // "New Moon"
    val forecast: String             // free text
) {
    val windDirectionCompass: String
        get() = when ((windDirection + 22) / 45 % 8) {
            0 -> "N"
            1 -> "NE"
            2 -> "E"
            3 -> "SE"
            4 -> "S"
            5 -> "SW"
            6 -> "W"
            7 -> "NW"
            else -> "N"
        }

    val isGoodSailing: Boolean
        get() = windSpeed >= 5.0 && windSpeed <= 25.0
}
