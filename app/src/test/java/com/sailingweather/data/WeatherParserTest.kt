package com.sailingweather.data

import com.sailingweather.model.WeatherConditions
import org.junit.Assert.*
import org.junit.Test

class WeatherParserTest {

    private val sampleHtml = """
        <html><body>
        Conditions as of: 8:41p 03/18/26
        Wind Speed: 1.7 kts | Direction: 315 deg | 10Min Avg: 2.6 kts | High Wind: 8.7 kts at: 1:34p
        Temp: 69.0 | Hum: 81 % | Bar: 29.992 Rising Slowly | Sunrise: 6:58a | Sunset: 7:03p | Moon Phase: New Moon
        Likely Forecast: Increasing clouds with little temperature change.
        </body></html>
    """.trimIndent()

    @Test
    fun `parse extracts timestamp`() {
        val result = WeatherParser.parse(sampleHtml)
        assertNotNull(result)
        assertEquals("8:41p 03/18/26", result!!.timestamp)
    }

    @Test
    fun `parse extracts wind data`() {
        val result = WeatherParser.parse(sampleHtml)!!
        assertEquals(1.7, result.windSpeed, 0.01)
        assertEquals(315, result.windDirection)
        assertEquals(2.6, result.windAvg10Min, 0.01)
        assertEquals(8.7, result.windHigh, 0.01)
        assertEquals("1:34p", result.windHighTime)
    }

    @Test
    fun `parse extracts conditions`() {
        val result = WeatherParser.parse(sampleHtml)!!
        assertEquals(69.0, result.temperature, 0.01)
        assertEquals(81, result.humidity)
        assertEquals(29.992, result.barometer, 0.001)
        assertEquals("Rising Slowly", result.barometerTrend)
    }

    @Test
    fun `parse extracts sun and moon`() {
        val result = WeatherParser.parse(sampleHtml)!!
        assertEquals("6:58a", result.sunrise)
        assertEquals("7:03p", result.sunset)
        assertEquals("New Moon", result.moonPhase)
    }

    @Test
    fun `parse extracts forecast`() {
        val result = WeatherParser.parse(sampleHtml)!!
        assertEquals("Increasing clouds with little temperature change.", result.forecast)
    }

    @Test
    fun `parse returns null for garbage input`() {
        val result = WeatherParser.parse("<html><body>Nothing here</body></html>")
        assertNull(result)
    }

    @Test
    fun `windDirectionCompass converts degrees correctly`() {
        val result = WeatherParser.parse(sampleHtml)!!
        assertEquals("NW", result.windDirectionCompass) // 315 degrees = NW
    }
}
