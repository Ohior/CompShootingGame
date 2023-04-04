package com.example.ohiorgamelib.button

import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ohiorgamelib.state.PressedState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OhGameButton(
    imageVector: ImageVector,
    function: @Composable (PressedState, Pair<Float, Float>?) -> Unit,
) {
    var pressedState by remember {
        mutableStateOf<PressedState>(PressedState.PressedNone)
    }
    var offset by remember {
        mutableStateOf<Pair<Float, Float>?>(null)
    }
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(50.dp)
            .background(Color.Red)
            .pointerInteropFilter { motionEvent ->
                offset = Pair(motionEvent.x, motionEvent.y)
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> pressedState = PressedState.PressedDown
                    MotionEvent.ACTION_UP -> pressedState = PressedState.PressedUp
                    MotionEvent.ACTION_MOVE -> pressedState = PressedState.PressedMove
                }
                true
            }

    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
    function(pressedState, offset)
}

@Composable
fun OhGameButton(
    @DrawableRes res: Int,
    function: @Composable (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
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

    function(isPressed)
}

@Composable
fun GamePadButton(
    upClick: () -> Unit,
    downClick: () -> Unit,
    leftClick: () -> Unit,
    rightClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OhGameButton(imageVector = Icons.Default.KeyboardArrowUp) { pressState, _ ->
            if (pressState == PressedState.PressedDown) upClick()
        }
        Row {
            OhGameButton(imageVector = Icons.Default.KeyboardArrowLeft) { pressState, _ ->
                if (pressState == PressedState.PressedDown) leftClick()
            }
            Spacer(modifier = Modifier.width(50.dp))
            OhGameButton(imageVector = Icons.Default.KeyboardArrowRight) { pressState, _ ->
                if (pressState == PressedState.PressedDown) rightClick()
            }
        }
        OhGameButton(imageVector = Icons.Default.KeyboardArrowDown) { pressState, _ ->
            if (pressState == PressedState.PressedDown) downClick()
        }
    }
}
