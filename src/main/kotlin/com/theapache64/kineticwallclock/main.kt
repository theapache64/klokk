import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.theapache64.kineticwallclock.composable.Clock
import com.theapache64.kineticwallclock.movement.getFlowerMatrix
import com.theapache64.kineticwallclock.movement.getStandByMatrix

const val COLUMNS = 15
const val ROWS = 8

private const val DIGIT_COLUMNS = 3
private const val DIGIT_ROWS = 6

sealed class Movement(
    val durationInMillis: Int = 2000,
) {
    data class StandBy(
        val degree: Int = 270,
    ) : Movement()

    data class Flower(
        val isOpen: Boolean = true,
    ) : Movement(
        durationInMillis = 4000
    )
}

class ClockData(
    val degreeOne: Int,
    val degreeTwo: Int,
)

fun main() {
    val clockSize = 60

    val clocksContainerWidth = clockSize * COLUMNS
    val clocksContainerHeight = clockSize * ROWS

    var activeMovement by mutableStateOf<Movement>(Movement.StandBy(270))
    val padding = 100

    Window(
        title = "Kinetic Wall Clock",
        size = IntSize(clocksContainerWidth + padding, clocksContainerHeight + padding)
    ) {

        val degreeMatrix = getDegreeMatrix(activeMovement)
        verifyIntegrity(degreeMatrix)
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            println("Rendering new clock...")

            repeat(ROWS) { i ->
                Row {
                    repeat(COLUMNS) { j ->
                        val clockData = degreeMatrix[i][j]
                        Clock(
                            _needleOneDegree = clockData.degreeOne,
                            _needleTwoDegree = clockData.degreeTwo,
                            durationInMillis = activeMovement.durationInMillis,
                            modifier = Modifier.requiredSize(clockSize.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                activeMovement = if (activeMovement is Movement.Flower) {
                    // close flower if open, and open if close
                    val activeFlower = activeMovement as Movement.Flower
                    activeFlower.copy(isOpen = !activeFlower.isOpen)
                } else {
                    // start new flower
                    Movement.Flower()
                }
            }
        ) {
            Text(text = "Animate :${activeMovement}")
        }
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


