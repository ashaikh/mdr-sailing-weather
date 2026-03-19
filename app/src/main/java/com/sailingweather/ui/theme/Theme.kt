package com.sailingweather.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = OceanBlue,
    onPrimary = NavyDark,
    primaryContainer = NavyLight,
    onPrimaryContainer = LightSkyBlue,
    secondary = SunsetGold,
    onSecondary = NavyDark,
    background = SurfaceDark,
    onBackground = TextOnDark,
    surface = CardDark,
    onSurface = TextOnDark,
    onSurfaceVariant = TextOnDarkSecondary,
    surfaceVariant = NavyMedium
)

private val LightColorScheme = lightColorScheme(
    primary = NavyLight,
    onPrimary = CardLight,
    primaryContainer = LightSkyBlue,
    onPrimaryContainer = NavyDark,
    secondary = SunsetOrange,
    onSecondary = CardLight,
    background = SurfaceLight,
    onBackground = NavyDark,
    surface = CardLight,
    onSurface = NavyDark,
    onSurfaceVariant = NavyMedium,
    surfaceVariant = LightSkyBlue
)

@Composable
fun SailingWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SailingTypography,
        content = content
    )
}
