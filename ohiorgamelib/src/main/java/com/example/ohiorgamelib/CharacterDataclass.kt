package com.example.ohiorgamelib

import androidx.compose.ui.graphics.Color

data class CharacterDataclass(
    val startValue:Float,
    val endValue:Float,
    val speedMillis:Int
)

data class GameCharacter(val xPos:Float, val yPos:Float, val size: Float, val color: Color = Color.Black)
data class GameMutableCharacter(var xPos:Float, var yPos:Float, var size: Float, var color: Color = Color.Black)
