package com.example.ohiorgamelib

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun OhGameSceneBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable GameState.() -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ) {
        GameState(this).content()
    }
}

@Composable
fun OhGameEngine(
    transition: InfiniteTransition = rememberInfiniteTransition(),
    framesPerSec: Float = 10f,
    durationMillis: Int = 1000,
    function: @Composable () -> Unit
) {
    val gameLoop by transition.animateFloat(
        initialValue = 0f,
        targetValue = framesPerSec,
        animationSpec = (infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ), repeatMode = RepeatMode.Restart
        ))
    )
    if (gameLoop != framesPerSec + 1) function()
}