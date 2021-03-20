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

sealed class Magics(
    val durationInMillis: Int = 2000,
) {
    class StandBy : Magics()
    class StandByTwo : Magics()
    class Flower : Magics()
}

class ClockData(
    val degreeOne: Int,
    val degreeTwo: Int,
)

fun main() {
    val clockSize = 60

    val clocksContainerWidth = clockSize * COLUMNS
    val clocksContainerHeight = clockSize * ROWS

    var currentMagic by mutableStateOf<Magics>(Magics.StandBy())
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
                currentMagic = Magics.Flower()
            }
        ) {
            Text(text = "Animate")
        }
    }
}

fun verifyIntegrity(degreeMatrix: List<List<ClockData>>) {
    for (matrix in degreeMatrix) {
        require(matrix.size == COLUMNS) { "" }
    }
}

fun getDegreeMatrix(currentMagic: Magics): List<List<ClockData>> {
    return when (currentMagic) {
        is Magics.StandBy -> getStandByMatrix(200)
        is Magics.StandByTwo -> getStandByMatrix(120)
        is Magics.Flower -> TODO()
    }
}

fun getStandByMatrix(
    standByDegree : Int
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
