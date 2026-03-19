package com.sailingweather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sailingweather.model.WeatherConditions

@Composable
fun ConditionsCard(conditions: WeatherConditions, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Thermostat,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "CURRENT CONDITIONS",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                ConditionItem(
                    icon = { Icon(Icons.Default.Thermostat, null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.secondary) },
                    label = "Temp",
                    value = "%.0f°F".format(conditions.temperature),
                    modifier = Modifier.weight(1f)
                )
                ConditionItem(
                    icon = { Icon(Icons.Default.WaterDrop, null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.secondary) },
                    label = "Humidity",
                    value = "${conditions.humidity}%",
                    modifier = Modifier.weight(1f)
                )
                ConditionItem(
                    icon = { Icon(Icons.Default.Speed, null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.secondary) },
                    label = "Barometer",
                    value = "%.3f\"".format(conditions.barometer),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "Barometer: ${conditions.barometerTrend}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun ConditionItem(
    icon: @Composable () -> Unit,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Spacer(Modifier.height(8.dp))
        Text(
            value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
