package com.example.ohiorgamelib

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

class GameState(boxWithConstraintsScope: BoxWithConstraintsScope) {
    val MAX_HEIGHT = boxWithConstraintsScope.maxHeight
    val MAX_WIDTH = boxWithConstraintsScope.maxWidth

    fun getHeightPercent(percentHeight: Float): Float = (MAX_HEIGHT.value * percentHeight) / 100

    fun getWidthPercent(percentWidth: Float): Float = (MAX_WIDTH.value * percentWidth) / 100


    @Composable
    fun shouldStartGameEngine(
        framesPerSec: Float = 10f,
        durationMillisSec: Int = 1000,
    ):Boolean {
        val transition: InfiniteTransition = rememberInfiniteTransition()
        val gameLoop by transition.animateFloat(
            initialValue = 0f,
            targetValue = framesPerSec,
            animationSpec = (infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillisSec,
                    easing = LinearEasing
                ), repeatMode = RepeatMode.Restart
            ))
        )
        return gameLoop != framesPerSec + 1f
    }
    @Composable
    fun ShouldStartGameEngine(
        framesPerSec: Float = 10f,
        durationMillisSec: Int = 1000,
        function:@Composable ()->Unit
    ){
        val transition: InfiniteTransition = rememberInfiniteTransition()
        val gameLoop by transition.animateFloat(
            initialValue = 0f,
            targetValue = framesPerSec,
            animationSpec = (infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillisSec,
                    easing = LinearEasing
                ), repeatMode = RepeatMode.Restart
            ))
        )
        if (gameLoop != framesPerSec + 1f)  function()
    }
}