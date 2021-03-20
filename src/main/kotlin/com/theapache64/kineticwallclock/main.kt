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
import com.theapache64.kineticwallclock.model.ClockData
import com.theapache64.kineticwallclock.movement.Movement
import com.theapache64.kineticwallclock.movement.getFlowerMatrix
import com.theapache64.kineticwallclock.movement.getStandByMatrix
import kotlinx.coroutines.delay


// Configs
const val COLUMNS = 15
const val ROWS = 8
const val PADDING = 100
const val CLOCK_SIZE = 60
const val CLOCKS_CONTAINER_WIDTH = CLOCK_SIZE * COLUMNS
const val CLOCKS_CONTAINER_HEIGHT = CLOCK_SIZE * ROWS
const val STAND_BY_DEGREE = 270

private const val DIGIT_COLUMNS = 3
private const val DIGIT_ROWS = 6

fun main() {


    Window(
        title = "Kinetic Wall Clock",
        size = IntSize(CLOCKS_CONTAINER_WIDTH + PADDING, CLOCKS_CONTAINER_HEIGHT + PADDING),
    ) {

        var activeMovement by remember { mutableStateOf<Movement>(Movement.StandBy(STAND_BY_DEGREE)) }
        val degreeMatrix = getDegreeMatrix(activeMovement)
        verifyIntegrity(degreeMatrix)

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            println("Rendering new clock...")

            repeat(ROWS) { i ->
                Row {
                    repeat(COLUMNS) { j ->
                        val clockData = degreeMatrix[i][j]
                        println("Anim duration is ${activeMovement.durationInMillis}")
                        Clock(
                            _needleOneDegree = clockData.degreeOne,
                            _needleTwoDegree = clockData.degreeTwo,
                            durationInMillis = activeMovement.durationInMillis,
                            modifier = Modifier.requiredSize(CLOCK_SIZE.dp)
                        )
                    }
                }
            }

            LaunchedEffect(Unit) {
                println("Launching..")
                val flower = Movement.Flower()
                val delay = flower.durationInMillis.toLong() + 250

                while (true) {
                    println("Delay is $delay")
                    delay(delay)
                    activeMovement = Movement.Flower(Movement.Flower.To.SQUARE)

                    delay(delay)
                    activeMovement = Movement.Flower(to = Movement.Flower.To.FLOWER)

                    delay(delay)
                    activeMovement = Movement.Flower(to = Movement.Flower.To.SQUARE)

                    /*delay(delay)
                    activeMovement = flower.copy(to = Movement.Flower.To.CIRCLE)*/

                    delay(delay)
                    activeMovement = Movement.Flower(to = Movement.Flower.To.STAR)
                }
            }
        }


        /*Button(
            onClick = {
                activeMovement = if (activeMovement is Movement.Flower) {
                    // close flower if open, and open if close
                    val activeFlower = activeMovement as Movement.Flower
                    when (activeFlower.state) {
                        Movement.Flower.State.STAND_BY -> {
                            activeFlower.copy(state = Movement.Flower.State.OPEN)
                        }
                        Movement.Flower.State.OPEN -> {
                            activeFlower.copy(state = Movement.Flower.State.STAND_BY)
                        }

                        Movement.Flower.State.MID -> {
                            activeFlower.copy(state = Movement.Flower.State.CLOSE)
                        }

                        Movement.Flower.State.CLOSE -> {
                            activeFlower.copy(state = Movement.Flower.State.STAND_BY)
                        }
                    }
                } else {
                    // start new flower
                    Movement.Flower()
                }
            }
        ) {
            Text(text = "Animate :${activeMovement}")
        }*/
    }
}

fun verifyIntegrity(degreeMatrix: List<List<ClockData>>) {
    for (matrix in degreeMatrix) {
        require(matrix.size == COLUMNS) { "Column size should be $COLUMNS but found ${matrix.size}" }
    }
    require(degreeMatrix.size == ROWS) { "Row size should be $ROWS but found ${degreeMatrix.size}" }
}

fun getDegreeMatrix(currentMagic: Movement): List<List<ClockData>> {
    return when (currentMagic) {
        is Movement.StandBy -> getStandByMatrix(currentMagic)
        is Movement.Flower -> getFlowerMatrix(currentMagic)
    }
}


