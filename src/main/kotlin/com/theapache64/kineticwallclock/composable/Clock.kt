package com.theapache64.kineticwallclock.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.theapache64.kineticwallclock.theme.CodGray
import com.theapache64.kineticwallclock.theme.CodGray_2
import kotlin.math.cos
import kotlin.math.sin


fun main(args: Array<String>) {
    Window {
        var needleOneDegree by remember { mutableStateOf(90) }
        var needleTwoDegree by remember { mutableStateOf(180) }

        Box(
            modifier = Modifier.fillMaxSize().background(CodGray)
        ) {
            val animationSpec = tween<Int>(500, easing = LinearEasing)

            val targetOne by animateIntAsState(
                needleOneDegree,
                animationSpec = animationSpec,
            )

            val targetTwo by animateIntAsState(
                needleTwoDegree,
                animationSpec = animationSpec,
            )

            Clock(
                _needleOneDegree = targetOne,
                _needleTwoDegree = targetTwo,
                modifier = Modifier.fillMaxSize()
            )

            Button(
                onClick = {
                    needleOneDegree = (0..360).random()
                    needleTwoDegree = (0..360).random()
                }
            ) {
                Text(text = "Animate")
            }
        }
    }
}

private const val NEEDLE_WIDTH = 30f
private const val NEEDLE_HALF = NEEDLE_WIDTH * 0.5f

@Composable
fun Clock(
    _needleOneDegree: Int = 270,
    _needleTwoDegree: Int = 0,
    modifier: Modifier = Modifier,
) {

    val needleOneDegree = _needleOneDegree * Math.PI / 180
    val needleTwoDegree = _needleTwoDegree * Math.PI / 180

    Canvas(
        modifier = modifier
    ) {

        // Background
        val radius = size.minDimension / 2f
        drawCircle(
            color = CodGray_2,
            radius = radius
        )

        // Needle One

        drawLine(
            color = Color.White,
            start = Offset(
                x = center.x,
                y = center.y,
            ),
            end = Offset(
                x = center.x + radius * sin(needleOneDegree).toFloat(),
                y = center.y + radius * cos(needleOneDegree).toFloat(),
            ),
            strokeWidth = NEEDLE_WIDTH
        )

        // Needle two
        drawLine(
            color = Color.White,
            start = Offset(
                x = center.x,
                y = center.y,
            ),
            end = Offset(
                x = center.x + radius * sin(needleTwoDegree).toFloat(),
                y = center.y + radius * cos(needleTwoDegree).toFloat(),
            ),
            strokeWidth = NEEDLE_WIDTH
        )
    }

}
