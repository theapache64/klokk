package com.theapache64.klokk

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import platform.AppKit.NSApp

fun main() {
    Window(
        title = "Klokk", size = DpSize(
            width = (CLOCKS_CONTAINER_WIDTH + PADDING).dp,
            height = (CLOCKS_CONTAINER_HEIGHT + PADDING + 40).dp

        )
    ) { KlokkApp() }

    NSApp?.run()
}