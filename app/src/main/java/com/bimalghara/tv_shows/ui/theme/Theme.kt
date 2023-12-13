package com.bimalghara.tv_shows.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val NightColorPalette = darkColors()
private val DayColorPalette = darkColors(
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
        colors = DayColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}