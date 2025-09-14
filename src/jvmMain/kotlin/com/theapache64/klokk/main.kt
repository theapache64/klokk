package com.theapache64.klokk

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Klokk",
        state = rememberWindowState(
            width = (CLOCKS_CONTAINER_WIDTH + PADDING).dp,
            height = (CLOCKS_CONTAINER_HEIGHT + PADDING + 40).dp
        ),
    ) { KlokkApp() }
}
