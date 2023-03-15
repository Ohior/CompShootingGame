package com.example.ohiorgamelib

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OhGameButton(
    imageVector: ImageVector,
    function: @Composable (PressedState) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var pressedState by remember {
        mutableStateOf<PressedState>(PressedState.PressedNone)
    }
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(50.dp)
            .background(Color.Red),
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
    pressedState = if (isPressed) {
        PressedState.PressedDown
    } else PressedState.PressedUp
    function(pressedState)
}

@Composable
fun OhGameButton(
    @DrawableRes res: Int,
    function: @Composable (PressedState) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var pressedState by remember {
        mutableStateOf<PressedState>(PressedState.PressedNone)
    }
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(50.dp)
            .background(Color.Red),
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painterResource(id = res),
            contentDescription = null
        )
    }
    pressedState = if (isPressed) {
        PressedState.PressedDown
    } else PressedState.PressedUp
    function(pressedState)
}