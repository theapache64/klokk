package com.theapache64.klokk.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material.icons.outlined.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.theapache64.klokk.IS_DEBUG
import com.theapache64.klokk.movement.core.Movement

@Composable
fun BottomToolBar(
    activeMovement: Movement, // to show debug info
    isAnimPlaying : Boolean,
    onTimeClicked: () -> Unit,
    onPlayClicked: () -> Unit,
    onStopClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                top = 18.dp,
                start = 50.dp,
                end = 20.dp,
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Play Button
        if(isAnimPlaying.not()){
            ToolBarIcon(
                imageVector = Icons.Outlined.PlayArrow,
                onClicked = onPlayClicked
            )
        }

        // Stop Button
        if(isAnimPlaying){
            ToolBarIcon(
                imageVector = Icons.Outlined.Stop,
                onClicked = onStopClicked
            )
        }

        // Time Button
        ToolBarIcon(
            imageVector = Icons.Outlined.Update,
            onClicked = onTimeClicked
        )


        // TextField

        // Align-Center

        // Debug Info
        if (IS_DEBUG) {
            Text(
                text = "DEBUG: $activeMovement",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun ToolBarIcon(
    imageVector: ImageVector,
    onClicked: () -> Unit,
) {
    IconButton(
        modifier = Modifier.background(
            color = CLOCK_BACKGROUND,
            shape = CircleShape
        ),
        onClick = onClicked
    ) {
        Icon(
            imageVector = imageVector,
            tint = Color.White,
            contentDescription = "ToolBar Icon"
        )
    }
}