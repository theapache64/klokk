package com.theapache64.klokk.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.theapache64.klokk.IS_DEBUG
import com.theapache64.klokk.movement.core.Movement

@Composable
fun BottomToolBar(
    isTvMode: Boolean = false,
    activeMovement: Movement, // to show debug info
    isAnimPlaying: Boolean,
    textInput: String,
    onShowTimeClicked: () -> Unit,
    onPlayClicked: () -> Unit,
    onStopClicked: () -> Unit,
    onTextInputChanged: (String) -> Unit,
    onHideControlsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val showTimeFocusRequester = remember { FocusRequester() }

    // Request focus on SHOW TIME button when toolbar appears
    LaunchedEffect(isAnimPlaying) {
        if (isAnimPlaying) {
            kotlinx.coroutines.delay(100) // Small delay to ensure button is composed
            showTimeFocusRequester.requestFocus()
        }
    }

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
        if (isAnimPlaying.not()) {
            IconTextButton(
                text = "START AUTOPLAY",
                imageVector = Icons.Outlined.PlayArrow,
                onClicked = onPlayClicked,
                isTvMode = isTvMode
            )
        }

        // Stop Button
        if (isAnimPlaying) {
            IconTextButton(
                text = "STOP AUTOPLAY",
                imageVector = Icons.Outlined.Stop,
                onClicked = onStopClicked,
                isTvMode = isTvMode
            )
        }

        // Time Button (default focus)
        if (isAnimPlaying) {
            IconTextButton(
                text = "SHOW TIME",
                imageVector = Icons.Outlined.Update,
                onClicked = onShowTimeClicked,
                focusRequester = showTimeFocusRequester,
                isTvMode = isTvMode
            )
        }

        // Immersive Mode Button
        IconTextButton(
            text = "HIDE CONTROLS",
            imageVector = Icons.Outlined.Fullscreen,
            onClicked = onHideControlsClicked,
            isTvMode = isTvMode
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            // TextField
            /*
            // TODO:
            OutlinedTextField(
                value = textInput,
                onValueChange = onTextInputChanged,
                placeholder = {
                    Text("Try some text here")
                }
            )*/

            // Debug Info
            if (IS_DEBUG) {
                Text(
                    text = "DEBUG: $activeMovement",
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun IconTextButton(
    text: String,
    imageVector: ImageVector,
    onClicked: () -> Unit,
    isTvMode: Boolean = false,
    focusRequester: FocusRequester? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    // Show focus highlighting only on TV
    val showFocusHighlight = isTvMode && isFocused

    Box(
        modifier = Modifier
            .then(
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                }
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .border(
                width = if (showFocusHighlight) 2.dp else 0.dp,
                color = if (showFocusHighlight) Color(0xFFFFD700) else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(if (showFocusHighlight) 2.dp else 0.dp)
    ) {
        OutlinedButton(
            onClick = onClicked,
            modifier = Modifier.focusable(),
            border = BorderStroke(
                width = 1.dp,
                color = if (showFocusHighlight) Color(0xFFFFD700) else Color.Gray
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = if (showFocusHighlight) Color(0xFFFFD700).copy(alpha = 0.2f) else Color.Transparent
            )
        ) {

            Icon(
                imageVector = imageVector,
                tint = if (showFocusHighlight) Color(0xFFFFD700) else Color.White,
                contentDescription = "ToolBar Icon",
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = text,
                color = if (showFocusHighlight) Color(0xFFFFD700) else Color.White
            )
        }
    }
}