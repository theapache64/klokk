import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.theapache64.kineticwallclock.composable.Clock

private const val COLUMNS = 15
private const val ROWS = 8

private const val DIGIT_COLUMNS = 3
private const val DIGIT_ROWS = 6

fun main() {
    var windowSize by mutableStateOf(IntSize.Zero)
    var degOne by mutableStateOf(0)
    var degTwo by mutableStateOf(0)

    Window(
        events = WindowEvents(
            onResize = {
                windowSize = it
            }
        ),
    ) {

        val clockWidth = windowSize.width / COLUMNS
        val clockHeight = windowSize.height / ROWS
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black)
        ) {
            for (i in 0 until ROWS) {
                Row {
                    for (j in 0 until COLUMNS) {
                        Clock(
                            _needleOneDegree = degOne,
                            _needleTwoDegree = degTwo,
                            modifier = Modifier.size(
                                width = clockWidth.dp,
                                height = clockHeight.dp
                            ).padding(10.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                degOne = (0..360).random()
                degTwo = (0..360).random()
            }
        ){
            Text(text = "Animate")
        }
    }
}