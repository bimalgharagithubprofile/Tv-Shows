package com.bimalghara.tv_shows.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val NightColorPalette = darkColorScheme()
private val DayColorPalette = lightColorScheme(
    primary = Orange,
    onPrimary = Light,
    background = LightGray,
    onBackground = Dark,
    surface = LightBlue,
    onSurface = Dark
)

@Composable
fun AppTheme(darkTheme: Boolean = false, content: @Composable() () -> Unit) {
    MaterialTheme(
        colorScheme = DayColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}