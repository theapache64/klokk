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

private const val COLUMNS = 15
private const val ROWS = 8

private const val DIGIT_COLUMNS = 3
private const val DIGIT_ROWS = 6

sealed class Movement(
    val durationInMillis: Int = 2000,
) {
    class StandBy(
        val degree: Int = 270,
    ) : Movement()

    class Flower : Movement()
}

class ClockData(
    val degreeOne: Int,
    val degreeTwo: Int,
)

fun main() {
    val clockSize = 60

    val clocksContainerWidth = clockSize * COLUMNS
    val clocksContainerHeight = clockSize * ROWS

    var currentMagic by mutableStateOf<Movement>(Movement.StandBy(270))
    val padding = 100

    Window(
        title = "Kinetic Wall Clock",
        size = IntSize(clocksContainerWidth + padding, clocksContainerHeight + padding)
    ) {

        val degreeMatrix = getDegreeMatrix(currentMagic)
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
                            modifier = Modifier.requiredSize(clockSize.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                currentMagic = Movement.Flower()
            }
        ) {
            Text(text = "Animate")
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
        is Movement.StandBy -> getStandByMatrix(currentMagic.degree)
        is Movement.Flower -> getFlowerMatrix()
    }
}

fun getFlowerMatrix(): List<List<ClockData>> {
    val oddDegreeOne = ClockData(
        degreeOne = 135,
        degreeTwo = 135
    )

    val oddDegreeTwo = ClockData(
        degreeOne = 225,
        degreeTwo = 225
    )

    val evenDegreeOne = ClockData(
        degreeOne = 45,
        degreeTwo = 45
    )

    val evenDegreeTwo = ClockData(
        degreeOne = 315,
        degreeTwo = 315
    )

    return mutableListOf<List<ClockData>>().apply {
        val columnRepeat = (COLUMNS / 2)
        repeat(ROWS) { rowIndex ->
            val row = mutableListOf<ClockData>()
            repeat(columnRepeat) {
                if (rowIndex % 2 == 0) {
                    // even row
                    row.add(evenDegreeOne)
                    row.add(evenDegreeTwo)
                } else {
                    // odd row
                    row.add(oddDegreeOne)
                    row.add(oddDegreeTwo)
                }
            }

            // filling remaining columns
            val remColumn = COLUMNS % 2
            if (remColumn > 0) {
                repeat(remColumn) { remColumnIndex ->
                    val lastColumn = if (remColumnIndex % 2 == 0) {
                        // even
                        if (rowIndex % 2 == 0) {
                            evenDegreeTwo
                        } else {
                            oddDegreeTwo
                        }
                    } else {
                        // odd
                        if (rowIndex % 2 == 0) {
                            evenDegreeOne
                        } else {
                            oddDegreeOne
                        }
                    }
                    row.add(lastColumn)
                }
            }

            add(row)
        }

        // has rem row
        val remRow = ROWS % 2

        // Add remaining row
        if (remRow == 1) {
            add(get(0))
        }
    }
}

/**
 * Creating a ROWSxCOLUMN matrix with given degree as ClockData
 */
fun getStandByMatrix(
    standByDegree: Int,
): List<List<ClockData>> {
    return mutableListOf<List<ClockData>>().apply {
        repeat(ROWS) {
            val list = mutableListOf<ClockData>()
            repeat(COLUMNS) {
                list.add(ClockData(standByDegree, standByDegree))
            }
            add(list)
        }
    }
}
