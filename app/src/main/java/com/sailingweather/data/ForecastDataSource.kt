package com.sailingweather.data

import com.sailingweather.model.ForecastPeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ForecastDataSource {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    suspend fun fetchForecast(): Result<List<ForecastPeriod>> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("https://api.weather.gov/gridpoints/LOX/148,43/forecast")
                .header("User-Agent", "SailingWeather/1.0")
                .header("Accept", "application/geo+json")
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("NWS API: ${response.code}"))
            }

            val json = response.body?.string()
                ?: return@withContext Result.failure(Exception("Empty NWS response"))

            val root = JSONObject(json)
            val periods = root.getJSONObject("properties").getJSONArray("periods")
            val forecasts = mutableListOf<ForecastPeriod>()

            for (i in 0 until minOf(periods.length(), 4)) {
                val p = periods.getJSONObject(i)
                forecasts.add(
                    ForecastPeriod(
                        name = p.getString("name"),
                        temperature = p.getInt("temperature"),
                        temperatureUnit = p.getString("temperatureUnit"),
                        windSpeed = p.getString("windSpeed"),
                        windDirection = p.getString("windDirection"),
                        shortForecast = p.getString("shortForecast"),
                        detailedForecast = p.getString("detailedForecast")
                    )
                )
            }

            Result.success(forecasts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
