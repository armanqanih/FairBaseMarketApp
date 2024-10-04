package org.lotka.xenon.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Green,
    background = Color.White,
    onBackground = LightGray,
    onPrimary = LightGreen,
    surface = MediumGray,
    onSurface = Black

)

@Composable
fun CleanArchitectureMovieAppTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}