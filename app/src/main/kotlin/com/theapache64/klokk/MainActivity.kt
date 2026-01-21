package com.theapache64.klokk

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {

    private var showControlsHandler: (() -> Unit)? = null
    private var areControlsVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Keep screen on to prevent auto-sleep
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Setup back press handler
        setupBackPressHandler()

        setContent {
            MainScreen(
                onControlsVisibilityChanged = { visible ->
                    areControlsVisible = visible
                    updateSystemUiVisibility(visible)
                },
                onShowControlsHandler = { handler ->
                    showControlsHandler = handler
                }
            )
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!areControlsVisible) {
                    // Controls are hidden, show them
                    showControlsHandler?.invoke()
                } else {
                    // Controls are visible, exit app
                    finish()
                }
            }
        })
    }

    private fun updateSystemUiVisibility(showSystemUI: Boolean) {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        if (showSystemUI) {
            // Show status bar and navigation bar
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        } else {
            // Hide status bar and navigation bar (immersive mode)
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
