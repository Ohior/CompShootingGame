package com.example.ohiorgamelib

import androidx.compose.foundation.layout.BoxWithConstraintsScope

class GameState(boxWithConstraintsScope: BoxWithConstraintsScope) {
    val MAX_HEIGHT = boxWithConstraintsScope.maxHeight
    val MAX_WIDTH = boxWithConstraintsScope.maxWidth

    fun getHeightPercent(percentHeight: Float): Float = (MAX_HEIGHT.value * percentHeight) / 100

    fun getWidthPercent(percentWidth: Float): Float = (MAX_WIDTH.value * percentWidth) / 100
}