package com.theapache64.klokk.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

actual fun getGoogleSansFont(): FontFamily = FontFamily(
    Font("fonts/GoogleSans-Regular.ttf", FontWeight.Normal),
    Font("fonts/GoogleSans-Medium.ttf", FontWeight.Medium),
    Font("fonts/GoogleSans-Bold.ttf", FontWeight.Bold),
)
