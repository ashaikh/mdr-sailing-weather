package com.sailingweather.data

import com.sailingweather.model.WeatherConditions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class WeatherDataSource {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    suspend fun fetchConditions(): Result<WeatherConditions> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("http://www.marinaaquaticcenter.org/weather/MAC_Conditions.htm")
                .header("User-Agent", "SailingWeather/1.0")
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
            }

            val html = response.body?.string()
                ?: return@withContext Result.failure(Exception("Empty response body"))

            val conditions = WeatherParser.parse(html)
                ?: return@withContext Result.failure(Exception("Failed to parse weather data"))

            Result.success(conditions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
