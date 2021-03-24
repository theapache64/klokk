package com.theapache64.klokk

import androidx.compose.desktop.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.theapache64.klokk.composable.BottomToolBar
import com.theapache64.klokk.composable.Clock
import com.theapache64.klokk.movement.core.Movement
import com.theapache64.klokk.theme.Black
import com.theapache64.klokk.theme.KlokkTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Configs
const val COLUMNS = 15
const val ROWS = 8
const val LETTER_WIDTH = 3
const val MAX_CHARS = COLUMNS / LETTER_WIDTH
const val DIGIT_COLUMNS = 3
const val DIGIT_ROWS = 6
const val PADDING = 100
const val CLOCK_SIZE = 60
const val CLOCKS_CONTAINER_WIDTH = CLOCK_SIZE * COLUMNS
const val CLOCKS_CONTAINER_HEIGHT = CLOCK_SIZE * ROWS
const val ENJOY_TIME_IN_MILLIS = 500L
const val IS_DEBUG = false
private val BACKGROUND_COLOR = Black


@ExperimentalFoundationApi
fun main() {

    Window(
        title = "Klokk",
        // Clock container plus the padding we need
        size = IntSize(CLOCKS_CONTAINER_WIDTH + PADDING, CLOCKS_CONTAINER_HEIGHT + PADDING + 40),
    ) {

        val infiniteTimeLoop = rememberCoroutineScope()

        // To hold and control movement transition
        var activeMovement by remember { mutableStateOf<Movement>(Movement.StandBy) }

        // To control the auto playing animation
        var shouldPlayAutoAnim by remember { mutableStateOf(true) }

        var textInput by remember { mutableStateOf("") }

        val isCenterAligned by remember { mutableStateOf(false) }

        // Generating degree matrix using the active movement
        val degreeMatrix = activeMovement.getMatrixGenerator().getVerifiedMatrix()

        KlokkTheme {
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
                LaunchedEffect(shouldPlayAutoAnim) {
                    println("Animation loop created and started -> will run? $shouldPlayAutoAnim")
                    val defaultWaitTime = activeMovement.durationInMillis.toLong() + ENJOY_TIME_IN_MILLIS
                    val mediumDelay = activeMovement.durationInMillis.toLong() - ENJOY_TIME_IN_MILLIS

                    while (shouldPlayAutoAnim) {
                        delay(ENJOY_TIME_IN_MILLIS)

                        /* activeMovement = Movement.Trance(Movement.Trance.To.SQUARE) // Show square
                         delay(mediumDelay)

                         activeMovement = Movement.Trance(to = Movement.Trance.To.FLOWER) // Then flower
                         delay(mediumDelay) // flower doesn't have enjoy time

                         activeMovement = Movement.Trance(to = Movement.Trance.To.STAR) // To star, through circle (auto)
                         delay(mediumDelay)

                         activeMovement = Movement.Trance(to = Movement.Trance.To.FLY) // then fly
                         delay(defaultWaitTime)*/

                        activeMovement = Movement.Ripple(to = Movement.Ripple.To.START) // then ripple start
                        delay(mediumDelay)

                        activeMovement = Movement.Ripple(to = Movement.Ripple.To.TIME_TABLE) // then ripple start
                        delay(defaultWaitTime)

                        activeMovement = Movement.Ripple(to = Movement.Ripple.To.END) // then ripple end
                        delay(mediumDelay)

                        activeMovement = Movement.Time() // then show time
                        delay(defaultWaitTime)
                    }
                }

                BottomToolBar(
                    activeMovement = activeMovement,
                    isAnimPlaying = shouldPlayAutoAnim,
                    textInput = textInput,
                    onTextInputChanged = { newInput ->
                        if (newInput.length <= MAX_CHARS) {
                            textInput = newInput.trim().toUpperCase()

                            if (textInput.isEmpty()) {
                                // no text
                                shouldPlayAutoAnim = true
                                activeMovement = Movement.StandBy
                            } else {
                                // has some text
                                shouldPlayAutoAnim = false
                                activeMovement = Movement.Text(textInput)
                            }
                        }
                    },

                    onShowTimeClicked = {
                        shouldPlayAutoAnim = false // stop auto play
                        infiniteTimeLoop.launch {
                            while (true) {
                                activeMovement = Movement.Time() // then show time
                                delay(activeMovement.durationInMillis.toLong())
                            }
                        }
                    },
                    onPlayClicked = {
                        shouldPlayAutoAnim = true
                        infiniteTimeLoop.cancel()
                    },
                    onStopClicked = {
                        shouldPlayAutoAnim = false
                        activeMovement = Movement.StandBy
                    }
                )
            }
        }
    }
}




