package com.theapache64.kineticwallclock.composable

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.theapache64.kineticwallclock.theme.CodGray
import com.theapache64.kineticwallclock.theme.CodGray_2
import kotlin.math.cos
import kotlin.math.sin

fun main(args: Array<String>) {
    Window {
        var needleOneDegree by remember { mutableStateOf(315) }
        var needleTwoDegree by remember { mutableStateOf(315) }

        Box(
            modifier = Modifier.fillMaxSize().background(CodGray)
        ) {

            Clock(
                _needleOneDegree = needleOneDegree,
                _needleTwoDegree = needleTwoDegree,
                modifier = Modifier.size(600.dp),
                durationInMillis = 2000
            )

            Button(
                onClick = {
                    needleOneDegree = 270 + 360
                    needleTwoDegree = 360
                }
            ) {
                Text(text = "Animate")
            }
        }
    }
}


private val NEEDLE_COLOR = Color.White


@Composable
fun Clock(
    _needleOneDegree: Int = 270,
    _needleTwoDegree: Int = 0,
    durationInMillis: Int = 500,
    easing: Easing = LinearEasing,
    modifier: Modifier = Modifier,
) {

    val needleOneDegree = (_needleOneDegree * Math.PI / 180).toFloat()
    val needleTwoDegree = (_needleTwoDegree * Math.PI / 180).toFloat()
    val animationSpec = tween<Float>(durationMillis = durationInMillis, easing = easing)

    val targetOne by animateFloatAsState(
        needleOneDegree,
        animationSpec = animationSpec
    )


    val targetTwo by animateFloatAsState(
        needleTwoDegree,
        animationSpec = animationSpec,
    )

    Canvas(
        modifier = modifier
    ) {

        val needleWidth = size.minDimension * 0.05f

        // Background
        val radius = size.minDimension / 2f

        drawCircle(
            color = CodGray_2,
            radius = radius
        )

        drawCircle(
            color = NEEDLE_COLOR,
            radius = needleWidth * 0.487f
        )


        // Needle One
        drawLine(
            color = NEEDLE_COLOR,
            start = center,
            end = Offset(
                x = center.x + radius * sin(targetOne),
                y = center.y - radius * cos(targetOne),
            ),
            strokeWidth = needleWidth
        )


        // Needle two
        drawLine(
            color = NEEDLE_COLOR,
            start = center,
            end = Offset(
                x = center.x + radius * sin(targetTwo),
                y = center.y - radius * cos(targetTwo),
            ),
            strokeWidth = needleWidth
        )

    }

}
