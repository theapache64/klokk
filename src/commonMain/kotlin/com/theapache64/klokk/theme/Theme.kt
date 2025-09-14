package com.theapache64.klokk.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightTheme = lightColors() // TODO :
val DarkTheme = darkColors(
    primary = Color.White
)

@Composable
fun KlokkTheme(
    isDark: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (isDark) DarkTheme else LightTheme,
    ) {
        Surface {
            content()
        }
    }
}