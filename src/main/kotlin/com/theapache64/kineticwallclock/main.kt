package com.theapache64.kineticwallclock

import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.theapache64.kineticwallclock.composable.Clock
import com.theapache64.kineticwallclock.movement.Movement
import kotlinx.coroutines.delay


// Configs
const val COLUMNS = 15
const val ROWS = 8
const val PADDING = 100
const val CLOCK_SIZE = 60
const val CLOCKS_CONTAINER_WIDTH = CLOCK_SIZE * COLUMNS
const val CLOCKS_CONTAINER_HEIGHT = CLOCK_SIZE * ROWS
const val ENJOY_TIME_IN_MILLIS = 500
val BACKGROUND_COLOR = Color.Black

private const val DIGIT_COLUMNS = 3
private const val DIGIT_ROWS = 6

fun main() {


    Window(
        title = "Kinetic Wall Clock",
        // Clock container plus the padding we need
        size = IntSize(CLOCKS_CONTAINER_WIDTH + PADDING, CLOCKS_CONTAINER_HEIGHT + PADDING),
    ) {

        // To hold and control movement transition
        var activeMovement by remember { mutableStateOf<Movement>(Movement.StandBy()) }

        // Generating degree matrix using the active movement
        val degreeMatrix = activeMovement.generateMatrix()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND_COLOR),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Building clock matrix
            repeat(ROWS) { i ->
                Row {
                    repeat(COLUMNS) { j ->
                        val clockData = degreeMatrix[i][j]
                        Clock(
                            _needleOneDegree = clockData.degreeOne,
                            _needleTwoDegree = clockData.degreeTwo,
                            durationInMillis = activeMovement.durationInMillis,
                            modifier = Modifier.requiredSize(CLOCK_SIZE.dp)
                        )
                    }
                }
            }

            // The animation loop
            LaunchedEffect(Unit) {
                println("Animation loop created and started..")
                val trance = Movement.Trance()
                val waitTime = trance.durationInMillis.toLong() + ENJOY_TIME_IN_MILLIS

                while (true) {

                    activeMovement = Movement.Trance(Movement.Trance.To.SQUARE) // Show square
                    delay(waitTime)

                    activeMovement = Movement.Trance(to = Movement.Trance.To.FLOWER) // Then flower
                    delay(waitTime - ENJOY_TIME_IN_MILLIS)

                    activeMovement = Movement.Trance(to = Movement.Trance.To.STAR) // To star, through circle (auto)
                    delay(waitTime)

                    activeMovement = Movement.Trance(to = Movement.Trance.To.FLY) // then fly
                    delay(waitTime)

                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.START)
                    delay(waitTime)

                    activeMovement = Movement.Ripple(to = Movement.Ripple.To.END)
                    delay(waitTime)
                }
            }
        }
    }
}




