package com.theapache64.klokk.composable

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.theapache64.klokk.theme.CodGray
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


private val NEEDLE_COLOR = Color.White
val CLOCK_BACKGROUND = CodGray

@Composable
fun Clock(
    needleOneDegree: Float = 270f,
    needleTwoDegree: Float = 0f,
    durationInMillis: Int = 500,
    delay: Int = 0,
    easing: Easing = LinearEasing,
    modifier: Modifier = Modifier,
) {

    val needleOneRadian = (needleOneDegree * PI / 180).toFloat()
    val needleTwoRadian = (needleTwoDegree * PI / 180).toFloat()
    val animationSpec = tween<Float>(durationMillis = durationInMillis, easing = easing, delayMillis = delay)

    val needleOneDegreeAnim by animateFloatAsState(
        needleOneRadian,
        animationSpec = animationSpec
    )

    val needleTwoDegreeAnim by animateFloatAsState(
        needleTwoRadian,
        animationSpec = animationSpec,
    )

    Canvas(
        modifier = modifier
    ) {

        val needleWidth = size.minDimension * 0.05f

        // Background
        val radius = size.minDimension / 2f

        drawCircle(
            color = CLOCK_BACKGROUND,
            radius = radius
        )

        // To make the needle origin rounded.
        drawCircle(
            color = NEEDLE_COLOR,
            radius = needleWidth * 0.487f
        )

        val radius2 = radius - 4

        // Needle One
        drawLine(
            color = NEEDLE_COLOR,
            start = center,
            end = Offset(
                // Finding end coordinate for the given radian
                x = center.x + radius2 * sin(needleOneDegreeAnim),
                y = center.y - radius2 * cos(needleOneDegreeAnim),
            ),
            strokeWidth = needleWidth
        )


        // Needle two
        drawLine(
            color = NEEDLE_COLOR,
            start = center,
            end = Offset(
                // Finding end coordinate for the given degree
                x = center.x + radius2 * sin(needleTwoDegreeAnim),
                y = center.y - radius2 * cos(needleTwoDegreeAnim),
            ),
            strokeWidth = needleWidth
        )
    }

}

//
//// Preview
//fun main() = application {
//    val windowState = rememberWindowState(size = DpSize(800.dp, 800.dp))
//    Window(
//        onCloseRequest = ::exitApplication,
//        title = "Klokk",
//        state = windowState,
//    ) {
//        var needleOneDegree by remember { mutableStateOf(135f) }
//        var needleTwoDegree by remember { mutableStateOf(225f) }
//
//
//        val scope = rememberCoroutineScope()
//
//        Box(
//            modifier = Modifier.fillMaxSize().background(Color.Black)
//        ) {
//
//            Clock(
//                needleOneDegree = needleOneDegree,
//                needleTwoDegree = needleTwoDegree,
//                modifier = Modifier.size(600.dp),
//                durationInMillis = 2000
//            )
//
//            Button(
//                onClick = {
//                    needleOneDegree = 110f
//                    needleTwoDegree = 170f
//
//                    /* scope.launch {
//                         delay(5000)
//                         needleOneDegree = (0..360).random().toFloat()
//                         needleTwoDegree = (0..360).random().toFloat()
//                     }*/
//                }
//            ) {
//                Text(text = "Animate")
//            }
//        }
//    }
//}
//
