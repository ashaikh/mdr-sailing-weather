package com.sailingweather.data

import com.sailingweather.model.WeatherConditions

object WeatherParser {

    fun parse(html: String): WeatherConditions? {
        val text = html.replace(Regex("<[^>]*>"), " ").replace(Regex("\\s+"), " ")

        val timestamp = Regex("""Conditions as of:\s*(.+?\d{2}/\d{2}/\d{2})""")
            .find(text)?.groupValues?.get(1)?.trim() ?: return null

        val windSpeed = Regex("""Wind Speed:\s*([\d.]+)\s*kts""")
            .find(text)?.groupValues?.get(1)?.toDoubleOrNull() ?: return null

        val windDir = Regex("""Direction:\s*(\d+)\s*deg""")
            .find(text)?.groupValues?.get(1)?.toIntOrNull() ?: return null

        val windAvg = Regex("""10Min Avg:\s*([\d.]+)\s*kts""")
            .find(text)?.groupValues?.get(1)?.toDoubleOrNull() ?: return null

        val windHigh = Regex("""High Wind:\s*([\d.]+)\s*kts""")
            .find(text)?.groupValues?.get(1)?.toDoubleOrNull() ?: return null

        val windHighTime = Regex("""High Wind:.*?at:\s*(\S+)""")
            .find(text)?.groupValues?.get(1)?.trim() ?: return null

        val temp = Regex("""Temp:\s*([\d.]+)""")
            .find(text)?.groupValues?.get(1)?.toDoubleOrNull() ?: return null

        val humidity = Regex("""Hum:\s*(\d+)\s*%""")
            .find(text)?.groupValues?.get(1)?.toIntOrNull() ?: return null

        val barometer = Regex("""Bar:\s*([\d.]+)\s+(.+?)\s*\|""")
            .find(text)

        val baroValue = barometer?.groupValues?.get(1)?.toDoubleOrNull() ?: return null
        val baroTrend = barometer?.groupValues?.get(2)?.trim() ?: return null

        val sunrise = Regex("""Sunrise:\s*(\S+)""")
            .find(text)?.groupValues?.get(1)?.trim() ?: return null

        val sunset = Regex("""Sunset:\s*(\S+)""")
            .find(text)?.groupValues?.get(1)?.trim() ?: return null

        val moonPhase = Regex("""Moon Phase:\s*(.+?)(?:\s*$|\s*Likely)""")
            .find(text)?.groupValues?.get(1)?.trim() ?: return null

        val forecast = Regex("""Likely Forecast:\s*(.+?)\.?\s*$""")
            .find(text)?.groupValues?.get(1)?.trim()?.let { "$it." } ?: "No forecast available."

        return WeatherConditions(
            timestamp = timestamp,
            windSpeed = windSpeed,
            windDirection = windDir,
            windAvg10Min = windAvg,
            windHigh = windHigh,
            windHighTime = windHighTime,
            temperature = temp,
            humidity = humidity,
            barometer = baroValue,
            barometerTrend = baroTrend,
            sunrise = sunrise,
            sunset = sunset,
            moonPhase = moonPhase,
            forecast = forecast
        )
    }
}
