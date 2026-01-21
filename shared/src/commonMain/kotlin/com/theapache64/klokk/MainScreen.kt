package com.theapache64.klokk

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.theapache64.klokk.composable.BottomToolBar
import com.theapache64.klokk.composable.Clock
import com.theapache64.klokk.movement.alphabet.TextMatrixGenerator
import com.theapache64.klokk.movement.core.Movement
import com.theapache64.klokk.theme.Black
import com.theapache64.klokk.theme.KlokkTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min


// Configs
const val COLUMNS = 15
const val ROWS = 8
const val PADDING = 100
const val CLOCK_SIZE = 60
const val CLOCKS_CONTAINER_WIDTH = CLOCK_SIZE * COLUMNS
const val CLOCKS_CONTAINER_HEIGHT = CLOCK_SIZE * ROWS
const val ENJOY_TIME_IN_MILLIS = 1500L
const val IS_DEBUG = true
private val BACKGROUND_COLOR = Black

// Toolbar approximate height
const val TOOLBAR_HEIGHT = 60

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    isTvMode: Boolean = false,
    onControlsVisibilityChanged: (Boolean) -> Unit = {},
    onShowControlsHandler: ((showControls: () -> Unit) -> Unit) = {}
) {

    /**
     * To control the infinite animation loop
     */
    val infiniteLoopScope = rememberCoroutineScope()

    // To hold and control movement transition
    var activeMovement by remember { mutableStateOf<Movement>(Movement.StandBy) }

    // To control the auto playing animation
    var shouldPlayAutoAnim by remember { mutableStateOf(true) }

    var textInput by remember { mutableStateOf("") }

    // To control toolbar visibility (immersive mode)
    var areControlsVisible by remember { mutableStateOf(true) }

    // Register handler to show controls (called from MainActivity on back press)
    LaunchedEffect(Unit) {
        onShowControlsHandler {
            areControlsVisible = true
        }
    }

    // Notify platform layer when controls visibility changes
    LaunchedEffect(areControlsVisible) {
        onControlsVisibilityChanged(areControlsVisible)
    }

    // Generating degree matrix using the active movement
    val degreeMatrix = activeMovement.getMatrixGenerator().getVerifiedMatrix()

    KlokkTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND_COLOR)
                .then(
                    // Only add clickable when controls are hidden (immersive mode)
                    if (!areControlsVisible) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            areControlsVisible = true
                        }
                    } else {
                        Modifier
                    }
                )
        ) {
            // Calculate responsive dimensions
            val density = LocalDensity.current
            val screenWidthPx = with(density) { maxWidth.toPx() }
            val screenHeightPx = with(density) { maxHeight.toPx() }

            // Calculate appropriate clock size and grid dimensions
            val padding = 16.dp
            val paddingPx = with(density) { padding.toPx() }
            val toolbarHeightPx = with(density) { TOOLBAR_HEIGHT.dp.toPx() }

            // Available space for clock grid
            val availableWidth = screenWidthPx - (paddingPx * 2)
            val availableHeight = screenHeightPx - toolbarHeightPx - (paddingPx * 2)

            // Calculate clock size to fit the grid (maintaining 15:8 ratio)
            val clockSizeByWidth = availableWidth / COLUMNS
            val clockSizeByHeight = availableHeight / ROWS
            val clockSizePx = min(clockSizeByWidth, clockSizeByHeight)
            val clockSize = with(density) { clockSizePx.toDp() }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Building clock matrix
                repeat(ROWS) { i ->
                    Row {
                        repeat(COLUMNS) { j ->
                            val clockData = degreeMatrix[i][j]
                            Clock(
                                needleOneDegree = clockData.degreeOne,
                                needleTwoDegree = clockData.degreeTwo,
                                durationInMillis = activeMovement.durationInMillis,
                                modifier = Modifier.requiredSize(clockSize)
                            )
                        }
                    }
                }

            // The animation loop with random order and time shown every minute
            LaunchedEffect(shouldPlayAutoAnim) {
                println("Animation loop created and started -> will run? $shouldPlayAutoAnim")
                val defaultWaitTime = activeMovement.durationInMillis.toLong() + ENJOY_TIME_IN_MILLIS

                // Patterns as list of sequences (some are paired START/END)
                val patternSequences: List<List<Movement>> = listOf(
                    // Trance patterns (single)
                    listOf(Movement.Trance(Movement.Trance.To.SQUARE)),
                    listOf(Movement.Trance(Movement.Trance.To.FLOWER)),
                    listOf(Movement.Trance(Movement.Trance.To.STAR)),
                    listOf(Movement.Trance(Movement.Trance.To.FLY)),
                    // Wave patterns (paired - START then END)
                    listOf(Movement.Wave(Movement.Wave.State.START), Movement.Wave(Movement.Wave.State.END)),
                    // Ripple patterns (paired - START then END)
                    listOf(Movement.Ripple(Movement.Ripple.To.START), Movement.Ripple(Movement.Ripple.To.END)),
                    listOf(Movement.Ripple(Movement.Ripple.To.TIME_TABLE)),
                    // Mosaic patterns - original
                    listOf(Movement.Mosaic(Movement.Mosaic.To.MONA_LISA)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.MANDALA)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.GALAXY)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.HYPNOTIC_RINGS)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.WAVE_INTERFERENCE)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.INFINITY)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.PEACOCK)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.VORTEX)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.FIBONACCI)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.OPTICAL_DEPTH)),
                    // Mosaic patterns - new hypnotic
                    listOf(Movement.Mosaic(Movement.Mosaic.To.KALEIDOSCOPE)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.AURORA)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.TORNADO)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.HEARTBEAT)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.BLACK_HOLE)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.ELECTRIC)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.OCEAN_WAVES)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.BLOOM)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.TESSERACT)),
                    listOf(Movement.Mosaic(Movement.Mosaic.To.STARBURST)),
                )

                // Track time for showing clock every minute
                var lastTimeShownAt = System.currentTimeMillis()
                val oneMinuteInMillis = 60_000L

                while (shouldPlayAutoAnim) {
                    // Shuffle pattern sequences for random order each cycle
                    val shuffledSequences = patternSequences.shuffled()

                    for (sequence in shuffledSequences) {
                        // Play all patterns in the sequence (keeps START/END together)
                        for (pattern in sequence) {
                            if (!shouldPlayAutoAnim) break

                            // Check if a minute has passed - show time
                            val now = System.currentTimeMillis()
                            if (now - lastTimeShownAt >= oneMinuteInMillis) {
                                activeMovement = Movement.Time()
                                delay(defaultWaitTime)
                                lastTimeShownAt = System.currentTimeMillis()
                                if (!shouldPlayAutoAnim) break
                            }

                            // Play the pattern
                            activeMovement = pattern
                            delay(defaultWaitTime)
                        }
                        if (!shouldPlayAutoAnim) break
                    }
                }
            }

                // Show toolbar only when controls are visible
                if (areControlsVisible) {
                    BottomToolBar(
                        isTvMode = isTvMode,
                        activeMovement = activeMovement,
                        isAnimPlaying = shouldPlayAutoAnim,

                        // TODO :WIP
                        textInput = textInput,
                        onTextInputChanged = { newInput ->
                            if (newInput.length <= TextMatrixGenerator.MAX_CHARS) {
                                textInput = newInput.trim().uppercase()

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
                            infiniteLoopScope.launch {
                                while (true) {
                                    activeMovement = Movement.Time() // then show time
                                    delay(activeMovement.durationInMillis.toLong())
                                }
                            }
                        },
                        onPlayClicked = {
                            shouldPlayAutoAnim = true
                            infiniteLoopScope.cancel()
                        },
                        onStopClicked = {
                            shouldPlayAutoAnim = false
                            activeMovement = Movement.StandBy
                        },
                        onHideControlsClicked = {
                            areControlsVisible = false
                        }
                    )
                }
            }
        }
    }
}
