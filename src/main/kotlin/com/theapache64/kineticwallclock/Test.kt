package com.theapache64.kineticwallclock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun main(args: Array<String>) {
    Window {
        var animDuration by remember { mutableStateOf(100) }
        var tempAnimDuration by remember { mutableStateOf("100") }
        var isTransparent by remember { mutableStateOf(false) }


        Column {

            val targetValue = if (isTransparent) {
                0f // hide it
            } else {
                1f // show it
            }

            println("targetValue is $targetValue")
            val targetAlpha by animateFloatAsState(
                targetValue,
                animationSpec = tween(animDuration)
            )


            Text(text = "Current Anim Duration is $animDuration")

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .alpha(targetAlpha)
                    .background(Color.Red)
            )

            TextField(
                value = tempAnimDuration,
                onValueChange = { newAnimDuration ->
                    tempAnimDuration = newAnimDuration
                }
            )

            Button(
                onClick = {
                    try {
                        isTransparent = !isTransparent
                        animDuration = tempAnimDuration.toInt()
                    } catch (e: NumberFormatException) {
                        // skip
                    }
                }
            ) {
                Text(text = "CHANGE ANIM DURATION to $tempAnimDuration && PLAY")
            }
        }
    }
}