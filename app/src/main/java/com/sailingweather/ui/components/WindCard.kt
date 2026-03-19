package com.sailingweather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sailingweather.model.WeatherConditions
import com.sailingweather.ui.theme.CautionWind
import com.sailingweather.ui.theme.GoodSailing
import com.sailingweather.ui.theme.HighWind

@Composable
fun WindCard(conditions: WeatherConditions, modifier: Modifier = Modifier) {
    val windColor = when {
        conditions.windSpeed < 5.0 -> MaterialTheme.colorScheme.onSurfaceVariant
        conditions.windSpeed <= 15.0 -> GoodSailing
        conditions.windSpeed <= 25.0 -> CautionWind
        else -> HighWind
    }

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Air,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "WIND",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(16.dp))

            // Main wind speed - big hero number
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "%.1f".format(conditions.windSpeed),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = windColor
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "kts",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(Modifier.weight(1f))

                // Direction compass
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Navigation,
                        contentDescription = "Wind direction",
                        modifier = Modifier
                            .size(32.dp)
                            .rotate(conditions.windDirection.toFloat()),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "${conditions.windDirectionCompass} ${conditions.windDirection}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(Modifier.height(12.dp))

            // Secondary wind stats
            Row(modifier = Modifier.fillMaxWidth()) {
                WindStat("10min Avg", "%.1f kts".format(conditions.windAvg10Min), Modifier.weight(1f))
                WindStat("High", "%.1f kts".format(conditions.windHigh), Modifier.weight(1f))
                WindStat("High At", conditions.windHighTime, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun WindStat(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
